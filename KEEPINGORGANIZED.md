# Keeping Organized

This guide is intended to help you maintain a well-organized and efficient repository structure. Follow these guidelines to ensure that the repository remains easy to navigate and collaborate on.

## Directory Structure

### Root Directory
- **archive**: Contains old or unmaintained projects.
- **docs**: Documentation files.
- **libs**: Libraries used across projects.
- **projects**: Project-specific directories, organized by year.
- **tests**: Test directories for various components.
- **README.md**: Overview and instructions for the repository.
- **CONTRIBUTING.md**: Guidelines for contributing to the repository.
- **LICENSE**: Licensing information for the repository.

### Project Structure
Each project should follow a consistent structure:
/project-name
├── build.gradle
├── gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
├── src
├── vendordeps
├── README.md
└── WPILib-License.md


## Adding a New Project

1. **Create the Project Directory**:
   - Name the directory clearly and descriptively.
   - Place it in the appropriate year folder within `projects`.

2. **Set Up the Project Structure**:
   - Ensure the project follows the standard structure outlined above.

3. **Document the Project**:
   - Add a `README.md` file with a brief description, setup instructions, usage guidelines, and contribution information.

## Organizing Existing Projects

1. **Move Unmaintained Projects**:
   - Move outdated or unmaintained projects to the `archive` directory.
   - Ensure the `archive` directory is organized by project and year.

2. **Maintain Consistent Structure**:
   - Ensure all projects follow the standard structure.
   - Update any projects that do not conform to the structure.

## Using Libraries

- Place all shared libraries in the `libs` directory.
- Ensure library files are named clearly and include version information.

## Documentation

- Keep all general documentation in the `docs` directory.
- Project-specific documentation should be included within the project's directory.

## Contributing to the Repository

- Follow the guidelines outlined in `CONTRIBUTING.md`.
- Use descriptive commit messages.
- Create feature branches for new features and bugfix branches for fixes.
- Submit pull requests for all changes and wait for review before merging.

## License Information

- Include appropriate licensing information in each project directory.
- The main repository is licensed under the MIT License, as described in the `LICENSE` file.
