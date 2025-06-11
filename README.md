# TimeTracker

🚧 **Work in Progress** 🚧

A time tracking and task management application being built with Kotlin Multiplatform and Compose Desktop. This project is in early development as I learn mobile/desktop development and explore GitHub.

## 🎯 Planned Features

- **Project Management**: Organize work by clients and projects
- **Task Tracking**: Create and categorize tasks with custom types and statuses
- **Time Tracking**: Track time spent on projects and individual tasks
- **Client Management**: Store client information with timezone support
- **Billable Hours**: Distinguish between billable and non-billable time
- **Reporting**: Generate time reports for billing purposes
- **Multi-language Support**: Starting with English and French
- **Desktop Application**: Native desktop app using Compose Desktop

## 🏗️ Tech Stack & Architecture

I'm building this with:

- **Kotlin Multiplatform**: For cross-platform compatibility
- **Compose Desktop**: Modern UI framework for desktop apps
- **SQLite Database**: Local storage with a well-structured schema
- **Java**: Backend logic and data access layer
- **Clean Architecture**: Separating concerns into different layers

### Project Structure

```
TimeTracker/
├── composeApp/           # Main application module
│   ├── src/desktopMain/  # Desktop-specific code
│   │   ├── java/         # Java backend (DAOs, services, utilities)
│   │   └── kotlin/       # Kotlin UI
│   └── src/desktopTest/  # Unit and integration tests
├── consoleApp/           # Console application for testing
└── build files
```

## 🗄️ Database Schema

The application uses SQLite with the following main entities:

- **Clients**: Customer information with timezone support
- **Projects**: Client projects with pricing and deadline tracking
- **Tasks**: Individual work items with status and type categorization
- **Time Entries**: Precise time tracking for projects and tasks
- **Task Types & Statuses**: Customizable categorization system

## 📋 Current Status

**What's Done:**
- ✅ Database schema design
- ✅ Project structure setup
- ✅ Model classes (Client, Project, Task, etc.)
- ✅ Basic Gradle configuration

**Currently Working On:**
- 🔄 Database connection and DAOs

**Next Steps:**
- ⏳ Basic UI screens with Compose Desktop
- ⏳ Core time tracking functionality
- ⏳ User interface development
- ⏳ Testing setup

## 🚀 Getting Started

**Note**: This project is still in early development. You can explore the code structure and database design, but the application isn't fully functional yet.

### Prerequisites

- JDK 11 or higher (using JetBrains Runtime 21.0.7)
- Basic knowledge of Kotlin (I'm learning too!)

### Exploring the Code

1. Clone the repository:
```bash
git clone https://github.com/Lucasbmmn/TimeTracker.git
cd TimeTracker
```

2. Check out the database schema:
  - Look at `composeApp/src/desktopMain/composeResources/files/database/schema.sql`

3. Explore the model classes:
  - Check out the classes in `composeApp/src/desktopMain/java/com/lucasbmmn/timetracker/model/`

### Building (when ready)

Once more components are implemented:
```bash
./gradlew build
```

### Running the desktop application:

```bash
./gradlew :composeApp:run
```

### Running console Application

For testing and development purposes, you can also run the console application:

```bash
./gradlew :consoleApp:run
```

## 🛠️ Development Notes

As a beginner to desktop development, I'm documenting my learning journey:

### What I've Learned So Far:
- Database design and relationships
- Kotlin Multiplatform project structure
- Model class creation and data modeling

### What I'm Currently Learning:
- Database connectivity with SQLite

### What I'm Learning Next:
- Compose Desktop UI development
- State management in Compose
- Testing strategies

### Key Components (Planned):
- **DatabaseManager**: SQLite connection handling
- **DAOs**: Data access for each entity type
- **Services**: Business logic layer
- **UI Screens**: Different app sections
- **Components**: Reusable UI elements

## 🌐 Internationalization

The app will supports multiple languages, including:
- English (default): `strings.xml`
- French: `strings-fr.xml`

To add a new language, create a new `strings-{locale}.xml` file in the resources directory.

## 📊 Database Design

The database follows a relational model with proper foreign key constraints:

- Projects belong to Clients
- Tasks belong to Projects
- Time Entries link to either Projects or Tasks
- Task Types and Statuses are configurable

## 🤝 Contributing & Learning Together

Since I'm new to this, I welcome:

- **Code reviews** and suggestions for improvement
- **Best practices** advice for Kotlin/Java development and development in general
- **Architecture feedback** on the current structure
- **Learning resources** you'd recommend

If you're also learning, feel free to:
1. Fork the repository and experiment
2. Share your learning discoveries
3. Ask questions through issues
4. Suggest improvements

### For Beginners Like Me:
- Each commit shows small, incremental progress
- Comments in code explain my thought process
- Issues document problems I'm solving
- Wiki (planned) will have learning resources I find helpful

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🏗️ Roadmap

**Phase 1 - Foundation (Current)**
- [x] Database schema design
- [x] Project structure setup
- [x] Model classes
- [ ] Database connectivity *(In progress)*
- [ ] Basic CRUD operations *(In progress)*

**Phase 2 - Core Features**
- [ ] Time tracking functionality
- [ ] Basic UI with Compose Desktop
- [ ] Project and task management
- [ ] Simple reporting

**Phase 3 - Polish & Features**
- [ ] Better UI/UX design
- [ ] Export functionality
- [ ] Multi-language support
- [ ] Testing implementation

**Future Ideas**
- [ ] Mobile app version
- [ ] Cloud sync capabilities
- [ ] Team features

## 📞 Questions & Support

Since I'm learning as I go:

- **Issues**: Feel free to create issues for bugs, questions, or suggestions
- **Discussions**: Use GitHub Discussions for general questions about the code or learning tips
- **Learning**: If you spot something that could be improved, please let me know!

## 🙏 Acknowledgments & Learning Resources

Resources that are helping me learn:
- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Desktop Documentation](https://www.jetbrains.com/lp/compose-desktop/)
- Various YouTube tutorials and blog posts (I'll add specific ones as I go)

Special thanks to the developer community for sharing knowledge and helping beginners!

---

**Learning in public! 📚✨**

*Feel free to follow along with my development journey. Every commit is a step forward in learning something new!*