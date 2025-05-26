package ba.unsa.etf.ppis.tim6.config;

import ba.unsa.etf.ppis.tim6.model.*;
import ba.unsa.etf.ppis.tim6.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoadDatabase {

    private final EventRepository eventRepository;
    private final IncidentRepository incidentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BackupRepository backupRepository;
    private final DocumentationRepository documentationRepository;
    private final ReportRepository reportRepository;
    private final DeviceRepository deviceRepository;
    private final SensorDataRepository sensorDataRepository;

    public LoadDatabase(EventRepository eventRepository,
                        IncidentRepository incidentRepository,
                        RoleRepository roleRepository,
                        UserRepository userRepository,
                        BackupRepository backupRepository,
                        DocumentationRepository documentationRepository,
                        ReportRepository reportRepository,
                        SensorDataRepository sensorDataRepository,
                        DeviceRepository deviceRepository) {
        this.eventRepository = eventRepository;
        this.incidentRepository = incidentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.backupRepository = backupRepository;
        this.documentationRepository = documentationRepository;
        this.reportRepository = reportRepository;
        this.sensorDataRepository = sensorDataRepository;
        this.deviceRepository = deviceRepository;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            incidentRepository.deleteAll();
            reportRepository.deleteAll();
            eventRepository.deleteAll();
            backupRepository.deleteAll();
            documentationRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            sensorDataRepository.deleteAll();
            deviceRepository.deleteAll();

            System.out.println("All previous entries removed from the database.");

            // Role Entries
            Role role1 = new Role();
            role1.setRoleName(Role.RoleName.ADMINISTRATOR);
            roleRepository.save(role1);

            Role role2 = new Role();
            role2.setRoleName(Role.RoleName.WAREHOUSE_MANAGER);
            roleRepository.save(role2);

            Role role3 = new Role();
            role3.setRoleName(Role.RoleName.WAREHOUSE_OPERATOR);
            roleRepository.save(role3);

            Role role4 = new Role();
            role4.setRoleName(Role.RoleName.MAINTENANCE_TEAM);
            roleRepository.save(role4);

            System.out.println("Sample role entries initialized.");

            // User Entries
            User user1 = new User();
            user1.setUsername("adminUser");
            user1.setPasswordHash("adminHash");  // Store hashed password
            user1.setRole(role1);  // Associate role with the user
            user1.setFirstName("John");
            user1.setLastName("Doe");
            user1.setEmail("admin@example.com");
            user1.setPhoneNumber("123-456-7890");
            user1.setStatus(User.Status.ACTIVE);
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("managerUser");
            user2.setPasswordHash("managerHash");  // Store hashed password
            user2.setRole(role2);  // Associate role with the user
            user2.setFirstName("Jane");
            user2.setLastName("Smith");
            user2.setEmail("manager@example.com");
            user2.setPhoneNumber("987-654-3210");
            user2.setStatus(User.Status.ACTIVE);
            userRepository.save(user2);

            User user3 = new User();
            user3.setUsername("operatorUser");
            user3.setPasswordHash("operatorHash");  // Store hashed password
            user3.setRole(role3);  // Associate role with the user
            user3.setFirstName("Emily");
            user3.setLastName("Johnson");
            user3.setEmail("operator@example.com");
            user3.setPhoneNumber("555-123-4567");
            user3.setStatus(User.Status.INACTIVE);
            userRepository.save(user3);

            User user4 = new User();
            user4.setUsername("maintenanceUser");
            user4.setPasswordHash("maintenanceHash");  // Store hashed password
            user4.setRole(role4);  // Associate role with the user
            user4.setFirstName("Michael");
            user4.setLastName("Williams");
            user4.setEmail("maintenance@example.com");
            user4.setPhoneNumber("444-321-6549");
            user4.setStatus(User.Status.ACTIVE);
            userRepository.save(user4);

            System.out.println("Sample user entries initialized.");

            // Event Entries
            Event event7 = new Event();
            event7.setEventType(Event.EventType.WARNING);
            event7.setDescription("Barcode scanning system delay reported in Zone B.");
            event7.setEventTime(LocalDateTime.now().minusHours(3));
            event7.setSeverityLevel(Event.SeverityLevel.LOW);
            event7.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event7);

            Event event1 = new Event();
            event1.setEventType(Event.EventType.WARNING);
            event1.setDescription("Flood detected in the primary data center. Immediate action required.");
            event1.setEventTime(LocalDateTime.now().minusDays(1));
            event1.setSeverityLevel(Event.SeverityLevel.CRITICAL);
            event1.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event1);

            Event event5 = new Event();
            event5.setEventType(Event.EventType.WARNING);
            event5.setDescription("Temperature deviation detected in cold storage section.");
            event5.setEventTime(LocalDateTime.now().minusHours(6));
            event5.setSeverityLevel(Event.SeverityLevel.MEDIUM);
            event5.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event5);

            Event event2 = new Event();
            event2.setEventType(Event.EventType.WARNING);
            event2.setDescription("Fire alarm triggered in the warehouse. Evacuation in progress.");
            event2.setEventTime(LocalDateTime.now().minusDays(3));
            event2.setSeverityLevel(Event.SeverityLevel.HIGH);
            event2.setStatus(Event.Status.COMPLETED);
            eventRepository.save(event2);

            Event event3 = new Event();
            event3.setEventType(Event.EventType.INFORMATION);
            event3.setDescription("Application server error, users unable to access the system.");
            event3.setEventTime(LocalDateTime.now().minusHours(2));
            event3.setSeverityLevel(Event.SeverityLevel.MEDIUM);
            event3.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event3);

            Event event4 = new Event();
            event4.setEventType(Event.EventType.WARNING);
            event4.setDescription("Flood warning for secondary backup systems. Monitoring in progress.");
            event4.setEventTime(LocalDateTime.now());
            event4.setSeverityLevel(Event.SeverityLevel.LOW);
            event4.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event4);

            Event event9 = new Event();
            event9.setEventType(Event.EventType.INFORMATION);
            event9.setDescription("Inventory software update applied successfully in warehouse system.");
            event9.setEventTime(LocalDateTime.now().minusDays(1).minusHours(2));
            event9.setSeverityLevel(Event.SeverityLevel.LOW);
            event9.setStatus(Event.Status.COMPLETED);
            eventRepository.save(event9);

            System.out.println("Sample event entries initialized.");

            // Entry 1
            Backup backup1 = new Backup();
            backup1.setBackupTime(LocalDateTime.now().minusDays(7));
            backup1.setBackupSize("1500 MB");
            backup1.setBackupLocation("AWS S3");
            backup1.setStatus(Backup.Status.SUCCESSFUL);
            backupRepository.save(backup1);

            // Entry 2
            Backup backup2 = new Backup();
            backup2.setBackupTime(LocalDateTime.now().minusDays(1));
            backup2.setBackupSize("2000 MB");
            backup2.setBackupLocation("Azure Blob");
            backup2.setStatus(Backup.Status.FAILED);
            backupRepository.save(backup2);

            // Entry 3
            Backup backup3 = new Backup();
            backup3.setBackupTime(LocalDateTime.now().plusDays(3));
            backup3.setBackupSize("1800 MB");
            backup3.setBackupLocation("Google Cloud Storage");
            backup3.setStatus(Backup.Status.PENDING);
            backupRepository.save(backup3);

            // Entry 4
            Backup backup4 = new Backup();
            backup4.setBackupTime(LocalDateTime.now().minusDays(3));
            backup4.setBackupSize("1300 MB");
            backup4.setBackupLocation("Google Cloud Storage");
            backup4.setStatus(Backup.Status.FAILED);
            backupRepository.save(backup4);

            System.out.println("Sample backup entries initialized.");

            // Entry 1: Warehouse Fire Response Plan
            String firePlanPath = new ClassPathResource("pdfs/Warehouse Fire Response Plan.pdf").getFile().getPath();
            byte[] firePlanBytes = Files.readAllBytes(Paths.get(firePlanPath));
            Documentation doc1 = new Documentation();
            doc1.setDocumentType(Documentation.DocumentType.EMERGENCY);
            doc1.setContent(firePlanBytes);
            doc1.setCreatedAt(LocalDateTime.now().minusDays(10));
            doc1.setCreatedBy(user4);
            doc1.setFileName("Warehouse Fire Response Plan.pdf");
            documentationRepository.save(doc1);

            // Entry 2: Power Outage Response Plan
            String powerOutagePath = new ClassPathResource("pdfs/Power Outage Response Plan.pdf").getFile().getPath();
            byte[] powerOutageBytes = Files.readAllBytes(Paths.get(powerOutagePath));
            Documentation doc2 = new Documentation();
            doc2.setDocumentType(Documentation.DocumentType.TECHNICAL_GUIDE);
            doc2.setContent(powerOutageBytes);
            doc2.setCreatedAt(LocalDateTime.now().minusDays(5));
            doc2.setCreatedBy(user4);
            doc2.setFileName("Power Outage Response Plan.pdf");
            documentationRepository.save(doc2);

            // Entry 3: Warehouse Flood Response Plan
            String floodPlanPath = new ClassPathResource("pdfs/Warehouse Flood Response Plan.pdf").getFile().getPath();
            byte[] floodPlanBytes = Files.readAllBytes(Paths.get(floodPlanPath));
            Documentation doc3 = new Documentation();
            doc3.setDocumentType(Documentation.DocumentType.EMERGENCY);
            doc3.setContent(floodPlanBytes);
            doc3.setCreatedAt(LocalDateTime.now().minusDays(1));
            doc3.setCreatedBy(user4);
            doc3.setFileName("Warehouse Flood Response Plan.pdf");
            documentationRepository.save(doc3);

            // Entry 4: Warehouse Network Connection Loss Response Plan
            String networkLossPath = new ClassPathResource("pdfs/Warehouse Network Connection Loss Response Plan.pdf").getFile().getPath();
            byte[] networkLossBytes = Files.readAllBytes(Paths.get(networkLossPath));
            Documentation doc4 = new Documentation();
            doc4.setDocumentType(Documentation.DocumentType.TECHNICAL_GUIDE);
            doc4.setContent(networkLossBytes);
            doc4.setCreatedAt(LocalDateTime.now().minusDays(2));
            doc4.setCreatedBy(user4);
            doc4.setFileName("Warehouse Network Connection Loss Response Plan.pdf");
            documentationRepository.save(doc4);

            System.out.println("Sample documentation entries initialized.");


            // Incident Entries
            Incident incident1 = new Incident();
            incident1.setTitle("Data Center Flood");
            incident1.setDescription("Water leakage detected in data center. Possible equipment damage.");
            incident1.setPriority(Incident.Priority.HIGH);
            incident1.setStatus(Incident.Status.OPEN);
            incident1.setDateReported(LocalDateTime.now().minusDays(1));
            incident1.setReportedBy(user1);
            incident1.setAssignedTo(user3);
            incident1.setEvent(event1);
            incidentRepository.save(incident1);

            Incident incident2 = new Incident();
            incident2.setTitle("Application Server Outage");
            incident2.setDescription("The affected server was rebooted in safe mode.");
            incident2.setPriority(Incident.Priority.MEDIUM);
            incident2.setStatus(Incident.Status.CLOSED);
            incident2.setDateReported(LocalDateTime.now().minusDays(3));
            incident2.setDateResolved(LocalDateTime.now().minusDays(2));
            incident2.setReportedBy(user2);
            incident2.setAssignedTo(user4);
            incident2.setEvent(event3);
            incident2.setActionTaken("Temporary barriers and absorbent materials were deployed to contain the water.");
            incidentRepository.save(incident2);

            Incident incident3 = new Incident();
            incident3.setTitle("Warehouse Fire");
            incident3.setDescription("Fire reported in warehouse. Evacuation in progress.");
            incident3.setPriority(Incident.Priority.HIGH);
            incident3.setStatus(Incident.Status.ESCALATED);
            incident3.setDateReported(LocalDateTime.now().minusHours(4));
            incident3.setReportedBy(user1);
            incident3.setAssignedTo(user4);
            incident3.setEvent(event2);
            incidentRepository.save(incident3);

            // Incident Entries
            Incident incident4 = new Incident();
            incident4.setTitle("Data Center Flood");
            incident4.setDescription("Water leakage detected in data center. Possible equipment damage.");
            incident4.setPriority(Incident.Priority.HIGH);
            incident4.setStatus(Incident.Status.OPEN);
            incident4.setDateReported(LocalDateTime.now().minusDays(1));
            incident4.setReportedBy(user1);
            incident4.setEvent(event1);
            incidentRepository.save(incident4);

            System.out.println("Sample incident entries initialized.");

            // Report Entries
            Report report1 = new Report();
            report1.setReportType(Report.ReportType.MONTHLY);
            report1.setContent("Monthly report covering system performance, incidents, and backups.");
            report1.setCreatedAt(LocalDateTime.now().minusDays(10));
            report1.setCreatedBy(user2);
            report1.setIncludeBackups(true);
            report1.setIncludeArticleStats(true);
            report1.setIncludeDeviceStats(true);
            report1.setIncludeEvents(true);
            report1.setIncludeIncidents(true);
            report1.setIncludeOrders(true);
            // Assuming user1 is the creator
            reportRepository.save(report1);

            Report report2 = new Report();
            report2.setReportType(Report.ReportType.QUARTERLY);
            report2.setContent("Quarterly report detailing performance metrics, incidents, and future plans.");
            report2.setCreatedAt(LocalDateTime.now().minusDays(30));
            report2.setCreatedBy(user2);
            report2.setIncludeBackups(true);
            report2.setIncludeArticleStats(true);
            report2.setIncludeDeviceStats(true);// Assuming user2 is the creator
            reportRepository.save(report2);

            Report report3 = new Report();
            report3.setReportType(Report.ReportType.YEARLY);
            report3.setContent("Yearly review covering overall system performance, incidents, and resolutions.");
            report3.setCreatedAt(LocalDateTime.now().minusMonths(3));
            report3.setCreatedBy(user2);
            report3.setIncludeDeviceStats(true);
            report3.setIncludeEvents(true);
            report3.setIncludeIncidents(true);
            report3.setIncludeOrders(true);// Assuming user1 is the creator
            reportRepository.save(report3);

            System.out.println("Sample report entries initialized.");

            Device device1 = new Device();
            device1.setName("Air-cooled condenser unit 3");
            device1.setType("Device 1");
            device1.setXCoordinate(630);
            device1.setYCoordinate(123);

            Device device2 = new Device();
            device2.setName("Air-cooled condenser unit 5");
            device2.setType("Device 2");
            device2.setXCoordinate(630);
            device2.setYCoordinate(270);

            Device device3 = new Device();
            device3.setName("Ventilation fan unit 5");
            device3.setType("Device 3");
            device3.setXCoordinate(630);
            device3.setYCoordinate(400);

            Device device4 = new Device();
            device4.setName("Window air conditioner 1");
            device4.setType("Device 4");
            device4.setXCoordinate(630);
            device4.setYCoordinate(500);

            Device device6 = new Device();
            device6.setName("Ventilation fan unit 3");
            device6.setType("Device 6");
            device6.setXCoordinate(435);
            device6.setYCoordinate(470);

            Device device7 = new Device();
            device7.setName("Ventilation fan unit 4");
            device7.setType("Device 7");
            device7.setXCoordinate(435);
            device7.setYCoordinate(520);

            Device device8 = new Device();
            device8.setName("Ventilation fan unit 2");
            device8.setType("Device 8");
            device8.setXCoordinate(285);
            device8.setYCoordinate(520);

            Device device9 = new Device();
            device9.setName("Air-cooled condenser unit 4");
            device9.setType("Device 9");
            device9.setXCoordinate(288);
            device9.setYCoordinate(280);

            Device device10 = new Device();
            device10.setName("Network cabinet 3");
            device10.setType("Device 10");
            device10.setXCoordinate(222);
            device10.setYCoordinate(320);

            Device device11 = new Device();
            device11.setName("Network cabinet 2");
            device11.setType("Device 11");
            device11.setXCoordinate(222);
            device11.setYCoordinate(260);

            Device device12 = new Device();
            device12.setName("Air-cooled condenser unit 2");
            device12.setType("Device 12");
            device12.setXCoordinate(410);
            device12.setYCoordinate(130);

            Device device13 = new Device();
            device13.setName("Power distribution system 1");
            device13.setType("Device 13");
            device13.setXCoordinate(410);
            device13.setYCoordinate(280);

            Device device14 = new Device();
            device14.setName("Power distribution system 2");
            device14.setType("Device 14");
            device14.setXCoordinate(510);
            device14.setYCoordinate(280);

            Device device15 = new Device();
            device15.setName("Air-cooled condenser unit 1");
            device15.setType("Device 15");
            device15.setXCoordinate(290);
            device15.setYCoordinate(130);

            Device device16 = new Device();
            device16.setName("Network cabinet 1");
            device16.setType("Device 16");
            device16.setXCoordinate(223);
            device16.setYCoordinate(120);

            Device device17 = new Device();
            device17.setName("Network cabinet 4");
            device17.setType("Device 17");
            device17.setXCoordinate(222);
            device17.setYCoordinate(420);

            Device device18 = new Device();
            device18.setName("Ventilation fan unit 1");
            device18.setType("Device 18");
            device18.setXCoordinate(284);
            device18.setYCoordinate(410);

            SensorData sensor1 = new SensorData();
            sensor1.setDischargePressure(212.5);
            sensor1.setFanMotorCurrent(5.3);
            sensor1.setTimestamp(LocalDateTime.now().minusMinutes(10));
            sensor1.setDevice(device1);

            SensorData sensor2 = new SensorData();
            sensor2.setDischargePressure(200);
            sensor2.setFanMotorCurrent(2.3);
            sensor2.setTimestamp(LocalDateTime.now().minusMinutes(9));
            sensor2.setDevice(device2);

            SensorData sensor3 = new SensorData();
            sensor3.setAirflowRate(1900.0);
            sensor3.setVibrationLevel(0.04);
            sensor3.setTimestamp(LocalDateTime.now().minusMinutes(8));
            sensor3.setDevice(device3);

            SensorData sensor4 = new SensorData();
            sensor4.setTemperature(21.8);
            sensor4.setEnergySaver(false);
            sensor4.setTimestamp(LocalDateTime.now().minusMinutes(7));
            sensor4.setDevice(device4);

            SensorData sensor6 = new SensorData();
            sensor6.setAirflowRate(1750.0);
            sensor6.setVibrationLevel(0.07);
            sensor6.setTimestamp(LocalDateTime.now().minusMinutes(5));
            sensor6.setDevice(device6);

            SensorData sensor7 = new SensorData();
            sensor7.setAirflowRate(1750.0);
            sensor7.setVibrationLevel(0.07);
            sensor7.setTimestamp(LocalDateTime.now().minusMinutes(4));
            sensor7.setDevice(device7);

            SensorData sensor8 = new SensorData();
            sensor8.setAirflowRate(1600.0);
            sensor8.setVibrationLevel(0.05);
            sensor8.setTimestamp(LocalDateTime.now().minusMinutes(3));
            sensor8.setDevice(device8);

            SensorData sensor9 = new SensorData();
            sensor9.setDischargePressure(211.1);
            sensor9.setFanMotorCurrent(2);
            sensor9.setTimestamp(LocalDateTime.now().minusMinutes(2));
            sensor9.setDevice(device9);

            SensorData sensor10 = new SensorData();
            sensor10.setPortSpeed(21);
            sensor10.setAccessLocked(false);
            sensor10.setTimestamp(LocalDateTime.now().minusMinutes(1));
            sensor10.setDevice(device10);

            SensorData sensor11 = new SensorData();
            sensor11.setPortSpeed(14);
            sensor11.setAccessLocked(false);
            sensor11.setTimestamp(LocalDateTime.now());
            sensor11.setDevice(device11);

            SensorData sensor12 = new SensorData();
            sensor12.setDischargePressure(210.5);
            sensor12.setFanMotorCurrent(3.3);
            sensor12.setTimestamp(LocalDateTime.now().minusHours(1));
            sensor12.setDevice(device12);

            SensorData sensor13 = new SensorData();
            sensor13.setVoltageOutput(230.0);
            sensor13.setCurrentLimit(20.0);
            sensor13.setTimestamp(LocalDateTime.now().minusHours(2));
            sensor13.setDevice(device13);

            SensorData sensor14 = new SensorData();
            sensor13.setVoltageOutput(210.0);
            sensor13.setCurrentLimit(23.0);
            sensor14.setTimestamp(LocalDateTime.now().minusHours(3));
            sensor14.setDevice(device14);

            SensorData sensor15 = new SensorData();
            sensor15.setDischargePressure(220.5);
            sensor15.setFanMotorCurrent(4.3);
            sensor15.setTimestamp(LocalDateTime.now().minusHours(4));
            sensor15.setDevice(device15);

            SensorData sensor16 = new SensorData();
            sensor16.setPortSpeed(16);
            sensor16.setAccessLocked(true);
            sensor16.setTimestamp(LocalDateTime.now().minusHours(5));
            sensor16.setDevice(device16);

            SensorData sensor17 = new SensorData();
            sensor16.setPortSpeed(16);
            sensor16.setAccessLocked(true);
            sensor17.setTimestamp(LocalDateTime.now().minusHours(6));
            sensor17.setDevice(device17);

            SensorData sensor18 = new SensorData();
            sensor18.setAirflowRate(1800.0);
            sensor18.setVibrationLevel(0.08);
            sensor18.setTimestamp(LocalDateTime.now().minusHours(7));
            sensor18.setDevice(device18);

            deviceRepository.save(device1);
            deviceRepository.save(device2);
            deviceRepository.save(device3);
            deviceRepository.save(device4);
            deviceRepository.save(device6);
            deviceRepository.save(device7);
            deviceRepository.save(device8);
            deviceRepository.save(device9);
            deviceRepository.save(device10);
            deviceRepository.save(device11);
            deviceRepository.save(device12);
            deviceRepository.save(device13);
            deviceRepository.save(device14);
            deviceRepository.save(device15);
            deviceRepository.save(device16);
            deviceRepository.save(device17);
            deviceRepository.save(device18);

            sensorDataRepository.save(sensor1);
            sensorDataRepository.save(sensor2);
            sensorDataRepository.save(sensor3);
            sensorDataRepository.save(sensor4);
            sensorDataRepository.save(sensor6);
            sensorDataRepository.save(sensor7);
            sensorDataRepository.save(sensor8);
            sensorDataRepository.save(sensor9);
            sensorDataRepository.save(sensor10);
            sensorDataRepository.save(sensor11);
            sensorDataRepository.save(sensor12);
            sensorDataRepository.save(sensor13);
            sensorDataRepository.save(sensor14);
            sensorDataRepository.save(sensor15);
            sensorDataRepository.save(sensor16);
            sensorDataRepository.save(sensor17);
            sensorDataRepository.save(sensor18);

            device1.setSensorDataList(List.of(sensor1));
            device2.setSensorDataList(List.of(sensor2));
            device3.setSensorDataList(List.of(sensor3));
            device4.setSensorDataList(List.of(sensor4));
            device6.setSensorDataList(List.of(sensor6));
            device7.setSensorDataList(List.of(sensor7));
            device8.setSensorDataList(List.of(sensor8));
            device9.setSensorDataList(List.of(sensor9));
            device10.setSensorDataList(List.of(sensor10));
            device11.setSensorDataList(List.of(sensor11));
            device12.setSensorDataList(List.of(sensor12));
            device13.setSensorDataList(List.of(sensor13));
            device14.setSensorDataList(List.of(sensor14));
            device15.setSensorDataList(List.of(sensor15));
            device16.setSensorDataList(List.of(sensor16));
            device17.setSensorDataList(List.of(sensor17));
            device18.setSensorDataList(List.of(sensor18));

        };
    }
}