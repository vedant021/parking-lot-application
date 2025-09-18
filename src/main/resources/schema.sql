-- User Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    role VARCHAR(50) NOT NULL
);

-- ParkingLot Table
CREATE TABLE parking_lot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255),
    floors INT
);

-- ParkingSlot Table
CREATE TABLE parking_slot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lot_id BIGINT NOT NULL,
    floor INT,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_parking_slot_lot FOREIGN KEY (lot_id) REFERENCES parking_lot(id)
);

-- Vehicle Table
CREATE TABLE vehicle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plate_no VARCHAR(50) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT fk_vehicle_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Ticket Table
CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id BIGINT NOT NULL,
    slot_id BIGINT NOT NULL,
    entry_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_ticket_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_ticket_slot FOREIGN KEY (slot_id) REFERENCES parking_slot(id)
);

-- Payment Table
CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id BIGINT NOT NULL UNIQUE,
    amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    CONSTRAINT fk_payment_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

-- PricingRule Table
CREATE TABLE pricing_rule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_type VARCHAR(20) NOT NULL,
    initial_hours INT NOT NULL,
    initial_fee DECIMAL(12,2) NOT NULL,
    hourly_fee DECIMAL(12,2) NOT NULL
);