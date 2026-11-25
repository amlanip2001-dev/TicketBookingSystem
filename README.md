# Movie Ticket Booking Engine (Core Java)
A thread-safe multilayered movie ticket booking engine build from scratch in Core Java. It stimulates concurrent cinema seat reservation, dynamic seat pricing, pluggable payment strategies, and atomic rollbacks on payment failure.  


# Key Engineering Highlights
- **Concurrency Control:** Prevents double-bookings under simultaneous user requests using `synchronizes(show)` monitor locks and a two-pass check-and-lock workflow.
- **Three-State Seat Lifecycle:** Transitions seats through `AVAILABLE` -> `LOCKED` -> `BOOKED` so slow payment operations runs outside synchronized blocks without interrupting other users.
- **Polymorphic Pricing:** Uses an abstract `Seat` hierarchy (`SilverSeat`, `GoldSeat`, `ReclinerSeat`) to compute ticket prices dynamically without `if-else` chain.
- **Strategy Pattern for payments:** Decouples payment gateways (`UpiPayment`, `CardPayment`) behind a `PaymentStrategy` interface with automatic seat rollback if payment fails.
- **Custom Exception Handling & Streams:** Separates failure states (`SeatUnavailableException`, `InvalidBookingException`) from normal flows and filters shows by movie and city using Java Streams.

## Architecture and Package Structure 

```text
src/com/ticketbooking/
├── model/      # Domain entities & state (Movie, Theatre, Screen, Show, Seat, Booking, User)
├── service/    # Core orchestration, search, thread locking & rollbacks (BookingService)
├── payment/    # Pluggable payment strategies (PaymentStrategy, UpiPayment, CardPayment)
├── exception/  # Domain-specific failure signals (SeatUnavailableException, InvalidBookingException)
└── Main.java   # Multi-threaded race-condition simulation & end-to-end verification
```

## How Seat Locking & Rollbacks Work

1. **Pass 1 (Validate All):** Inside `synchronized(show)`, checks that every requested seats are `AVAILABLE`. If any seat taken, `SeatUnavailableException` is thrown immediately before changing any state.
2. **Pass 2 (Lock All):** Marks all selected seats as `LOCKED` and exits the synchronized block so other users can book differnt seats concurrently.
3. **Payment Execution:** Calls `paymentStrategy.pay(double amount)` outside the lock.
4. **Commit or Rollback: **
    -**Success:** Updates seats from `LOCKED` -> `BOOKED`, sets booking to `CONFIRMED`, and saves the booking.
    -**Failure /Exception:** Reverts seats from `LOCKED` -> `AVAILABLE` and sets booking to `CANCELLED` .

## How to Run 

**Prerequisites:** Java 17 or higher

```bash
git clone https://github.com/amlanip2001-dev/TicketBookingSystem.git
cd TicketBookingSystem
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.ticketbooking.Main
```

## Roadmap
- [x] Core Java layered domain model & polymorphic pricing
- [x] Multi-threaded seat locking and atomic failure rollbacks
- [x] Strategy pattern payments & stream-based show search 
- [] Spring Boot Rest API migration & Relational Database persistence
- [] Interactive frontend seat-selection UI 

