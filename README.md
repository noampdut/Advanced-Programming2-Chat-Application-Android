#### Advanced project for the Advanced Programming course : Web Development

This document provides comprehensive instructions and highlights for installing and executing our chat application, which supports web, Android, and React platforms.

## Prerequisites
Before running the project, ensure you have the following installed:

1. .NET Core SDK (for the server)
2. npm (for the React client)
3. Node.js (for the React client)
4. Android Studio (for the Android client)

## Running the Program

### Step 1: Run the ASP.NET Project (Server)
First, set up and run the ASP.NET project from the following repository:
[ASP.NET Project Repository](https://github.com/noampdut/Share-Drive-Server-Side.git)

1. Clone the repository.
2. Open a terminal and navigate to the project directory.
3. Run `dotnet restore` to restore dependencies.
4. Run `dotnet run` to start the server.

> **Note:** Ensure the server runs on port 5001 to maintain compatibility with the clients.

### Step 2: Run the Android Project
To run the Android client:

1. Open Android Studio and import the project.
2. Sync the project with Gradle files.
3. Run the application on an emulator or a physical device.

### Step 3: Run the React Project
We also support a React-based web client. Follow these steps to run the React project:

1. Clone the React project from the following repository:
   [React Project Repository](https://github.com/noampdut/Chat-Project-Frontend-Side.git)
2. Navigate to the directory.
3. Execute the following commands:

```bash
npm install
npm start
```

## User Credentials

### Admin Account
- **Username:** admin
- **Password:** n123456

### Additional Registered Users
- **Username:** noampdut
- **Password:** n123456
- **Username:** naama
- **Password:** n123456
- **Username:** ofek
- **Password:** n123456

## Registration Instructions
To register a new user, enter a username without spaces and a password containing at least 6 characters, including both letters and digits.

## Additional Notes
- The server (API project) should be running on port 5001.
- In the settings page, enter the complete base URL, e.g., `http://10.0.2.2:5001/api/`.
- To add a new contact from the React client when the server is on the same computer, use `localhost:5001`.
- For chatting between two different devices, first log in, and once both devices are connected to the app, you can start chatting.

## Thank You and Enjoy!
