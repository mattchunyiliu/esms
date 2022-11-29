package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.endpoint.AttachmentEndpoint;
import kg.kundoluk.school.service.storage.AttachmentFileService;
import kg.kundoluk.school.service.storage.LocalFileStorageService;
import kg.kundoluk.school.service.student.StudentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Endpoint
public class AttachmentEndpointImpl implements AttachmentEndpoint {
    private final AttachmentFileService attachmentFileService;
    private final LocalFileStorageService localFileStorageService;
    private final StudentService studentService;

    public AttachmentEndpointImpl(AttachmentFileService attachmentFileService, LocalFileStorageService localFileStorageService, StudentService studentService) {
        this.attachmentFileService = attachmentFileService;
        this.localFileStorageService = localFileStorageService;
        this.studentService = studentService;
    }

    @Override
    public AttachmentFileResponseDto uploadMultipartFile(MultipartFile file) throws IOException {
        return attachmentFileService.uploadMultipartFile(file);
    }

    @Async
    @Override
    public Resource match(MultipartFile file, Long schoolId) throws IOException {

        String path = System.getProperty("user.dir") + "/uploads/" + saveFile(file);
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        localFileStorageService.deleteFile(path);

        String row;

        int i = 0;

        String csvPath = System.getProperty("user.dir") + "/uploads/" +FilenameUtils.removeExtension(file.getOriginalFilename())+"-"+ System.currentTimeMillis() + "_output.csv";
        FileWriter csvWriter = new FileWriter(csvPath);

        ArrayList<ArrayList<String>> rows = new ArrayList<>();


        ArrayList<String> header = new ArrayList<>();

        header.add("StudentName,StudentID,Class,CartID");
        rows.add(header);

        List<StudentViewDTO> students = studentService.getStudentListBySchool(schoolId, false);
        while ((row = csvReader.readLine()) != null) {

            i++;
            if (i == 1 || (row.equals("StudentName,StudentID,Class,StudentID"))) continue;
            String[] data = row.split(",");
            String[] temp = data[0].split("[ ]{1,}");

            String firstName = null;
            String lastName = null;
            try {
                firstName = temp[1];
                lastName = temp[0];
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            if (temp.length > 2) {
                lastName = lastName + " " + firstName;
                firstName = temp[2];
            }

            // STUDENT TITLE COLUMN //
            ArrayList<String> singleList = new ArrayList<String>();
            singleList.add(lastName + " " + firstName);


            // STUDENT ID COLUMN //
            String finalFirstName = firstName;
            String finalLastName = lastName;
            Optional<StudentViewDTO> student = students
                    .stream()
                    .filter(s->s.getFirstName().equalsIgnoreCase(finalFirstName.trim()) && s.getLastName().equals(finalLastName.trim()))
                    .findFirst();
            if (student.isPresent()){
                singleList.add(student.get().getId().toString());
            } else {
                singleList.add("Not found");
            }
            // CLASS COLUMN //
            singleList.add("");


            try {
                if (data.length >= 3)
                    singleList.add(data[3]);
                else
                    singleList.add("");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            rows.add(singleList);

        }

        for (List<String> rowData : rows) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvReader.close();

        return localFileStorageService.loadFileAsResource(csvPath);
    }

    private String saveFile(MultipartFile file) {
        if (file == null) {
            return "";
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFilename = "import-" + LocalDateTime.now().withNano(0) + "." + extension;

        return localFileStorageService.storeFile(file, newFilename);
    }
}
