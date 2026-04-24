package it.kevien.demo.notificationservice.service;

import it.kevien.demo.notificationservice.model.Customer;
import it.kevien.demo.notificationservice.model.Email;
import it.kevien.demo.notificationservice.repository.CustomerRepository;
import it.kevien.demo.sharedevents.model.inventory.StockFailed;
import it.kevien.demo.sharedevents.model.payment.PaymentConfirmed;
import it.kevien.demo.sharedevents.model.payment.PaymentFailed;

import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    private final CustomerRepository customerRepository;
    private final JavaMailSender mailSender;

    public NotificationService(CustomerRepository customerRepository, JavaMailSender mailSender) {
        this.customerRepository = customerRepository;
        this.mailSender = mailSender;
    }

    public void sendEmail(PaymentConfirmed event) {
        String email = this.retrieveCustomerEmail(event.customerId());
        this.send(new Email("Payment confirmed!", "Payment of €" + event.amount() + " completed successfully", email));
    }

    public void sendEmail(PaymentFailed event) {
        String email = this.retrieveCustomerEmail(event.customerId());
        this.send(new Email("Payment failed!", "Payment failed. Reason: " + event.reason() + "\nPlease, retry.", email));
    }

    public void sendEmail(StockFailed event) {
        String email = this.retrieveCustomerEmail(event.customerId());
        this.send(new Email("Order not allowed", "Order not allowed. Reason: " + event.reason() + "\nPlease, retry.", email));
    }

    private void send(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@ecommerce-kafka.com");
        message.setTo(email.to());
        message.setSubject(email.subject());
        message.setText(email.body());
        mailSender.send(message);
        log.info("[NOTIFICATION] Email sent - to={}, subject={}", email.to(), email.subject());
    }

    private String retrieveCustomerEmail(String customerId) {
        Customer customer = customerRepository.findById(customerId);
        return customer.email();
    }
}
