package kg.kundoluk.school.config.rabbit;

import kg.kundoluk.school.config.properties.RabbitMQProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @Bean
    Queue gradeQueue() {
        return new Queue("gradeQueue", false );
    }

    @Bean
    Queue quarterGradeQueue() {
        return new Queue("quarterGradeQueue", false );
    }

    @Bean
    Queue attendanceQueue() {
        return new Queue("attendanceQueue", false);
    }

    @Bean
    Queue assignmentQueue() {
        return new Queue("assignmentQueue", false);
    }

    @Bean
    Queue subscriptionQueue() {
        return new Queue("subscriptionQueue", false );
    }

    @Bean
    Queue chatQueue() {
        return new Queue("chatQueue", false );
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rabbitMQProperties.getExchangeName());
    }

    @Bean
    Binding gradeBinding(Queue gradeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(gradeQueue).to(topicExchange).with("queue.grade");
    }

    @Bean
    Binding quarterGradeBinding(Queue quarterGradeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(quarterGradeQueue).to(topicExchange).with("queue.quarter.grade");
    }

    @Bean
    Binding attendanceBinding(Queue attendanceQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(attendanceQueue).to(topicExchange).with("queue.card.attendance");
    }

    @Bean
    Binding assignmentBinding(Queue assignmentQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(assignmentQueue).to(topicExchange).with("queue.assignment");
    }

    @Bean
    Binding subscriptionGradeBinding(Queue subscriptionQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(subscriptionQueue).to(topicExchange).with("queue.subscription");
    }

    @Bean
    Binding chatBinding(Queue chatQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(chatQueue).to(topicExchange).with("queue.chat");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
