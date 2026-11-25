import com.ticketbooking.model.*;
import com.ticketbooking.payment.CardPayment;
import com.ticketbooking.payment.UpiPayment;
import com.ticketbooking.service.BookingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main{
    public static void main(String []args) throws InterruptedException{

        BookingService bookingService=new BookingService();

        Movie movie=new Movie("M1", "Intersteller", Genre.SCI_FI, 145);

        List<Seat> seats=List.of(
                new SilverSeat("A1", 200.0),
                new SilverSeat("A2" , 200.0),
                new GoldSeat("B1" , 350.0),
                new ReclinerSeat("C1", 500.0 )
        );
        Screen screen=new Screen("S1", "Audi1", seats);
        Theatre theatre=new Theatre("TH1", "PVR Phoenix", "Mohali", List.of(screen));
        Show show=new Show("SH1", theatre, movie, screen,LocalDateTime.now().plusHours(3));

        bookingService.addTheatre(theatre);
        bookingService.addShow(show);

        List<Show> foundShow= bookingService.searchShowsByMovieAndCity("InterSteller", "Mohali");
        System.out.println("Found shows : " +foundShow.size());


        User user1=new User("U1", "Rohan", "rohan@domain.com");
        User user2=new User("U2", "Amlan", "amlan@domain.com");

        java.util.concurrent.atomic.AtomicReference<Booking> confirmedBooking=new java.util.concurrent.atomic.AtomicReference<>();
        Runnable task1= () ->{
            try{
                Booking b1=bookingService.bookTickets(
                        user1, show, List.of("B1", "C1"),new UpiPayment("rohan@upi")
                );
                confirmedBooking.set(b1);
                System.out.println("\n[Thread 1- Rohan] BOOKING SUCCESS:\n" + b1.generateTicketReceipt());
            }catch (Exception e){
                System.out.println("\n[Thread 1- Rohan] BOOKING FAILED : " +e.getMessage());
            }
        };
        Runnable task2= () -> {
            try{
                Booking b2=bookingService.bookTickets(user2, show, List.of("B1", "C1"),new CardPayment("4111222233334444","Amlan"));
                confirmedBooking.set(b2);
                System.out.println("\n[Thread 2- Amlan] BOOKING SUCESS:\n" +b2.generateTicketReceipt());
            }catch (Exception e){
                System.out.println("\n[Thread 2- Amlan] BOOKING FAILED: " +e.getMessage());
            }
        };
        Thread t1=new Thread(task1);
        Thread t2=new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Booking winner =confirmedBooking.get();
        if(winner != null){
            System.out.println("\nCancelling booking: " +winner.getBookingId());
            bookingService.cancelBooking(winner.getBookingId());
            System.out.println("Booking status after cancellation: "+winner.getStatus());
            System.out.println("Seat B1 status now: " +show.getSeat("B1").getStatus());

            try {
                Booking rebooked= bookingService.bookTickets(user2, show, List.of("B1","C1"),new CardPayment("4111222233334444", "Amlan"));
                System.out.println("\n[Amlan Retry] BOOKING SUCCESS:\n" +rebooked.generateTicketReceipt());
            }catch (Exception e){
                System.out.println("\n[Amlan Retry] FAILED: " +e.getMessage());
            }
        }

    }
}