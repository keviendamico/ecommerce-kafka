package it.kevien.demo.paymentservice.service;

import it.kevien.demo.paymentservice.producer.PaymentConfirmedProducer;
import it.kevien.demo.paymentservice.producer.PaymentFailedProducer;
import it.kevien.demo.sharedevents.model.inventory.StockReserved;
import it.kevien.demo.sharedevents.model.payment.PaymentConfirmed;
import it.kevien.demo.sharedevents.model.payment.PaymentFailed;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class PaymentService {

    private final PaymentConfirmedProducer paymentConfirmedProducer;
    private final PaymentFailedProducer paymentFailedProducer;

    public PaymentService(PaymentConfirmedProducer paymentConfirmedProducer, PaymentFailedProducer paymentFailedProducer) {
        this.paymentConfirmedProducer = paymentConfirmedProducer;
        this.paymentFailedProducer = paymentFailedProducer;
    }

    public void processStockReserved(StockReserved stock) {
        // Simulate a payment with an external gateway: 80% success
        boolean paymentConfirmed = ThreadLocalRandom.current().nextDouble() < 0.8;
        if (!paymentConfirmed) {
            log.warn("[PAYMENT] Payment declined - orderId={}", stock.orderId());
            paymentFailedProducer.send(new PaymentFailed(stock.orderId(), stock.customerId(), "Payment declined", Instant.now().toEpochMilli()));
            return;
        }
        String paymentId = UUID.randomUUID().toString();
        log.info("[PAYMENT] Payment confirmed - orderId={}, paymentId={}", stock.orderId(), paymentId);
        paymentConfirmedProducer.send(new PaymentConfirmed(stock.orderId(), stock.customerId(), paymentId, stock.totalAmount(), Instant.now().toEpochMilli()));
    }
}
