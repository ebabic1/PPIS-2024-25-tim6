package ba.unsa.etf.ppis.tim6.config;

import ba.unsa.etf.ppis.tim6.model.*;
import ba.unsa.etf.ppis.tim6.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class LoadDatabase {

    private final EventRepository eventRepository;
    private final IncidentRepository incidentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BackupRepository backupRepository;
    private final DocumentationRepository documentationRepository;
    private final ReportRepository reportRepository;

    public LoadDatabase(EventRepository eventRepository,
                        IncidentRepository incidentRepository,
                        RoleRepository roleRepository,
                        UserRepository userRepository,
                        BackupRepository backupRepository,
                        DocumentationRepository documentationRepository,
                        ReportRepository reportRepository) {
        this.eventRepository = eventRepository;
        this.incidentRepository = incidentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.backupRepository = backupRepository;
        this.documentationRepository = documentationRepository;
        this.reportRepository = reportRepository;
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
            Event event1 = new Event();
            event1.setEventType(Event.EventType.FLOOD);
            event1.setDescription("Flood detected in the primary data center. Immediate action required.");
            event1.setEventTime(LocalDateTime.now().minusDays(1));
            event1.setSeverityLevel(Event.SeverityLevel.CRITICAL);
            event1.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event1);

            Event event2 = new Event();
            event2.setEventType(Event.EventType.FIRE);
            event2.setDescription("Fire alarm triggered in the warehouse. Evacuation in progress.");
            event2.setEventTime(LocalDateTime.now().minusDays(3));
            event2.setSeverityLevel(Event.SeverityLevel.HIGH);
            event2.setStatus(Event.Status.COMPLETED);
            eventRepository.save(event2);

            Event event3 = new Event();
            event3.setEventType(Event.EventType.APPLICATION_ERROR);
            event3.setDescription("Application server error, users unable to access the system.");
            event3.setEventTime(LocalDateTime.now().minusHours(2));
            event3.setSeverityLevel(Event.SeverityLevel.MEDIUM);
            event3.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event3);

            Event event4 = new Event();
            event4.setEventType(Event.EventType.FLOOD);
            event4.setDescription("Flood warning for secondary backup systems. Monitoring in progress.");
            event4.setEventTime(LocalDateTime.now());
            event4.setSeverityLevel(Event.SeverityLevel.LOW);
            event4.setStatus(Event.Status.ACTIVE);
            eventRepository.save(event4);

            System.out.println("Sample event entries initialized.");

            // Entry 1
            Backup backup1 = new Backup();
            backup1.setBackupTime(LocalDateTime.now().minusDays(7));
            backup1.setBackupSize(1500); // Size in MB
            backup1.setBackupLocation("AWS S3 - Bucket A");
            backup1.setStatus(Backup.Status.SUCCESSFUL);
            backupRepository.save(backup1);

            // Entry 2
            Backup backup2 = new Backup();
            backup2.setBackupTime(LocalDateTime.now().minusDays(1));
            backup2.setBackupSize(2000); // Size in MB
            backup2.setBackupLocation("Azure Blob - Container B");
            backup2.setStatus(Backup.Status.FAILED);
            backupRepository.save(backup2);

            // Entry 3
            Backup backup3 = new Backup();
            backup3.setBackupTime(LocalDateTime.now());
            backup3.setBackupSize(1800); // Size in MB
            backup3.setBackupLocation("Google Cloud Storage - Bucket C");
            backup3.setStatus(Backup.Status.SUCCESSFUL);
            backupRepository.save(backup3);

            System.out.println("Sample backup entries initialized.");

            // Entry 1: Emergency Document
            Documentation doc1 = new Documentation();
            doc1.setDocumentType(Documentation.DocumentType.EMERGENCY);
            doc1.setContent("This document provides guidelines for handling emergency situations in the system.");
            doc1.setCreatedAt(LocalDateTime.now().minusDays(10));
            doc1.setCreatedBy(user1);
            documentationRepository.save(doc1);

            // Entry 2: Technical Guide
            Documentation doc2 = new Documentation();
            doc2.setDocumentType(Documentation.DocumentType.TECHNICAL_GUIDE);
            doc2.setContent("This technical guide describes the deployment process for the application.");
            doc2.setCreatedAt(LocalDateTime.now().minusDays(5));
            doc2.setCreatedBy(user2);
            documentationRepository.save(doc2);

            // Entry 3: Another Emergency Document
            Documentation doc3 = new Documentation();
            doc3.setDocumentType(Documentation.DocumentType.EMERGENCY);
            doc3.setContent("Steps to follow during data breaches or unauthorized access incidents.");
            doc3.setCreatedAt(LocalDateTime.now().minusDays(1));
            doc3.setCreatedBy(user1);
            documentationRepository.save(doc3);

            System.out.println("Sample documentation entries initialized.");

            // Incident Entries
            Incident incident1 = new Incident();
            incident1.setTitle("Data Center Flood");
            incident1.setDescription("Water leakage detected in data center. Possible equipment damage.");
            incident1.setPriority(Incident.Priority.HIGH);
            incident1.setStatus(Incident.Status.OPEN);
            incident1.setDateReported(LocalDateTime.now().minusDays(1));
            incident1.setReportedBy(user1);
            incident1.setAssignedTo(user2);
            incident1.setEvent(event1);
            incidentRepository.save(incident1);

            Incident incident2 = new Incident();
            incident2.setTitle("Application Server Outage");
            incident2.setDescription("Application server went down, impacting users.");
            incident2.setPriority(Incident.Priority.MEDIUM);
            incident2.setStatus(Incident.Status.CLOSED);
            incident2.setDateReported(LocalDateTime.now().minusDays(3));
            incident2.setDateResolved(LocalDateTime.now().minusDays(2));
            incident2.setReportedBy(user2);
            incident2.setAssignedTo(user1);
            incident2.setEvent(event3);
            incidentRepository.save(incident2);

            Incident incident3 = new Incident();
            incident3.setTitle("Warehouse Fire");
            incident3.setDescription("Fire reported in warehouse. Evacuation in progress.");
            incident3.setPriority(Incident.Priority.HIGH);
            incident3.setStatus(Incident.Status.ESCALATED);
            incident3.setDateReported(LocalDateTime.now().minusHours(4));
            incident3.setReportedBy(user1);
            incident3.setAssignedTo(user2);
            incident3.setEvent(event2);
            incidentRepository.save(incident3);

            System.out.println("Sample incident entries initialized.");

            // Report Entries
            Report report1 = new Report();
            report1.setReportType(Report.ReportType.MONTHLY);
            report1.setContent("Monthly report covering system performance, incidents, and backups.");
            report1.setCreatedAt(LocalDateTime.now().minusDays(10));
            report1.setCreatedBy(user1);  // Assuming user1 is the creator
            reportRepository.save(report1);

            Report report2 = new Report();
            report2.setReportType(Report.ReportType.QUARTERLY);
            report2.setContent("Quarterly report detailing performance metrics, incidents, and future plans.");
            report2.setCreatedAt(LocalDateTime.now().minusDays(30));
            report2.setCreatedBy(user2);  // Assuming user2 is the creator
            reportRepository.save(report2);

            Report report3 = new Report();
            report3.setReportType(Report.ReportType.YEARLY);
            report3.setContent("Yearly review covering overall system performance, incidents, and resolutions.");
            report3.setCreatedAt(LocalDateTime.now().minusMonths(3));
            report3.setCreatedBy(user1);  // Assuming user1 is the creator
            reportRepository.save(report3);

            System.out.println("Sample report entries initialized.");
        };
    }
}