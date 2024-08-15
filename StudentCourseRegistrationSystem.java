import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolled;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolled = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public boolean isFull() {
        return enrolled >= capacity;
    }

    public void enroll() {
        if (!isFull()) {
            enrolled++;
        }
    }

    public void drop() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + ", Description: " + description +
               ", Capacity: " + capacity + ", Schedule: " + schedule + ", Enrolled: " + enrolled;
    }
}

class Student {
    private String studentID;
    private String name;
    private Course[] registeredCourses;
    private int registeredCourseCount;

    public Student(String studentID, String name, int maxCourses) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new Course[maxCourses];
        this.registeredCourseCount = 0;
    }

    public String getStudentID() {
        return studentID;
    }

    public void registerCourse(Course course) {
        if (!course.isFull() && !isCourseRegistered(course)) {
            registeredCourses[registeredCourseCount++] = course;
            course.enroll();
            System.out.println("Successfully registered for course: " + course.getCourseCode());
        } else {
            System.out.println("Failed to register for course: " + course.getCourseCode());
        }
    }

    public void dropCourse(Course course) {
        for (int i = 0; i < registeredCourseCount; i++) {
            if (registeredCourses[i] == course) {
                for (int j = i; j < registeredCourseCount - 1; j++) {
                    registeredCourses[j] = registeredCourses[j + 1];
                }
                registeredCourses[--registeredCourseCount] = null;
                course.drop();
                System.out.println("Successfully dropped course: " + course.getCourseCode());
                return;
            }
        }
        System.out.println("You are not registered for this course: " + course.getCourseCode());
    }

    private boolean isCourseRegistered(Course course) {
        for (int i = 0; i < registeredCourseCount; i++) {
            if (registeredCourses[i] == course) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder courses = new StringBuilder();
        for (int i = 0; i < registeredCourseCount; i++) {
            courses.append(registeredCourses[i].getCourseCode()).append(", ");
        }
        return "Student ID: " + studentID + ", Name: " + name + ", Registered Courses: " + courses.toString();
    }
}
public class StudentCourseRegistrationSystem {
    private static Course[] courses;
    private static Student[] students;
    private static int courseCount;
    private static int studentCount;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        courses = new Course[10];  // Maximum of 10 courses
        students = new Student[10];  // Maximum of 10 students
        courseCount = 0;
        studentCount = 0;

        // Sample data
        addCourse(new Course("INT101", "Intro to Java", "Basics of Programming", 30, "MWF 10-11AM"));
        addCourse(new Course("CSE205", "Data Structures & Algorithm", "Develops Problem Solving Skills", 40, "TTh 1-2:30PM"));

        addStudent(new Student("S001", "Anshika Nigam", 5));  // Maximum of 5 courses per student
        addStudent(new Student("S002", "Muskan Singh", 5));  // Maximum of 5 courses per student

        while (true) {
            System.out.println("\n1. List Courses\n2. Register Course\n3. Drop Course\n4. List Students\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    listStudents();
                    break;
                case 5:
                    System.exit(0);
            }
        }
}
private static void addCourse(Course course) {
    if (courseCount < courses.length) {
        courses[courseCount++] = course;
    } else {
        System.out.println("Course list is full.");
    }
}

private static void addStudent(Student student) {
    if (studentCount < students.length) {
        students[studentCount++] = student;
    } else {
        System.out.println("Student list is full.");
    }
}

private static void listCourses() {
    System.out.println("Available Courses:");
    for (int i = 0; i < courseCount; i++) {
        System.out.println(courses[i]);
    }
}

private static void registerCourse() {
    System.out.print("Enter student ID: ");
    String studentID = scanner.nextLine();
    System.out.print("Enter course code: ");
    String courseCode = scanner.nextLine();

    Student student = findStudent(studentID);
    Course course = findCourse(courseCode);

    if (student != null && course != null) {
        student.registerCourse(course);
    } else {
        System.out.println("Invalid student ID or course code.");
    }
}

private static void dropCourse() {
    System.out.print("Enter student ID: ");
    String studentID = scanner.nextLine();
    System.out.print("Enter course code: ");
    String courseCode = scanner.nextLine();

    Student student = findStudent(studentID);
    Course course = findCourse(courseCode);

    if (student != null && course != null) {
        student.dropCourse(course);
    } else {
        System.out.println("Invalid student ID or course code.");
    }
}

private static void listStudents() {
    System.out.println("Registered Students:");
    for (int i = 0; i < studentCount; i++) {
        System.out.println(students[i]);
    }
}

private static Student findStudent(String studentID) {
    for (int i = 0; i < studentCount; i++) {
        if (students[i].getStudentID().equals(studentID)) {
            return students[i];
        }
    }
    return null;
}

private static Course findCourse(String courseCode) {
    for (int i = 0; i < courseCount; i++) {
        if (courses[i].getCourseCode().equals(courseCode)) {
            return courses[i];
        }
    }
    return null;
}
}
