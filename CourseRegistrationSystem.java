import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing a Course
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    // Constructor to initialize course details
    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    // Getters for course details
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public void enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
        }
    }

    public void dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }
}

// Class representing a Student
class Student {
    private String name;
    private List<Course> registeredCourses;

    // Constructor to initialize student details
    public Student(String studentID, String name) {
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // Method to register for a course
    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.getAvailableSlots() > 0) {
            registeredCourses.add(course);
            course.enrollStudent();
            System.out.println(name + " successfully registered for " + course.getTitle());
        } else {
            System.out.println("Registration failed: No available slots or already registered.");
        }
    }

    // Method to drop a course
    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
            System.out.println(name + " successfully dropped " + course.getTitle());
        } else {
            System.out.println("You are not registered in this course.");
        }
    }

    // Display registered courses
    public void displayRegisteredCourses() {
        System.out.println("Courses registered by " + name + ":");
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            for (Course course : registeredCourses) {
                System.out.println(course.getTitle() + " (" + course.getCourseCode() + ")");
            }
        }
    }
}

// Class to manage the courses and student registration
class CourseManager {
    private List<Course> courses;
    private List<Student> students;

    // Constructor to initialize the course and student lists
    public CourseManager() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        loadCourses();
    }

    // Method to load predefined courses into the system
    private void loadCourses() {
        courses.add(new Course("CSE101", "Introduction to Computer Science", "Basic concepts of computer science", 30, "Mon, Wed 10:00-11:30 AM"));
        courses.add(new Course("MTH102", "Calculus I", "Introduction to calculus and differential equations", 40, "Tue, Thu 1:00-2:30 PM"));
        courses.add(new Course("PHY103", "Physics I", "Fundamentals of mechanics and thermodynamics", 35, "Mon, Wed 2:00-3:30 PM"));
        courses.add(new Course("ENG104", "English Literature", "Study of classic and modern English literature", 25, "Fri 9:00-11:00 AM"));
    }

    // Method to display available courses
    public void displayAvailableCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course.getCourseCode() + ": " + course.getTitle() + " | Slots Available: " + course.getAvailableSlots());
        }
    }

    // Method to add a student to the system
    public void addStudent(Student student) {
        students.add(student);
    }

    // Method to find a course by course code
    public Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

// Main class to run the Course Registration System
public class CourseRegistrationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseManager courseManager = new CourseManager();

        // Create a sample student
        Student student = new Student("S001", "John Doe");
        courseManager.addStudent(student);

        boolean continueProgram = true;

        // Main loop for user interaction
        while (continueProgram) {
            System.out.println("\n--- Course Registration System ---");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Display available courses
                    courseManager.displayAvailableCourses();
                    break;
                case 2:
                    // Register for a course
                    System.out.print("Enter the course code to register: ");
                    String courseCode = scanner.next();
                    Course courseToRegister = courseManager.findCourse(courseCode);
                    if (courseToRegister != null) {
                        student.registerCourse(courseToRegister);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 3:
                    // Drop a course
                    System.out.print("Enter the course code to drop: ");
                    String courseCodeToDrop = scanner.next();
                    Course courseToDrop = courseManager.findCourse(courseCodeToDrop);
                    if (courseToDrop != null) {
                        student.dropCourse(courseToDrop);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 4:
                    // View registered courses
                    student.displayRegisteredCourses();
                    break;
                case 5:
                    // Exit the program
                    continueProgram = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
        scanner.close();
    }
}
