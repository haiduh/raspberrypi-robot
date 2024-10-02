
# SwiftBot - The Smart Roaming Robot 🤖

SwiftBot is a smart roaming robot that detects objects and interacts with its surroundings based on different behavioral modes. This robot project uses Raspberry Pi for controlling movements and object detection via sensors, making it capable of curious exploration or scaredy avoidance.

## Features ✨
- **Curious Mode**: The robot approaches objects, maintaining a safe distance. Moves closer if too far or further if too close.
- **Scaredy Mode**: The robot avoids objects, moving away once they come too close.
- **Rotational Movement**: If no object is detected, the robot rotates 120° and continues its search.
- **Visual Feedback**: LED lights change color based on object proximity (green for found, red for danger, blinking for avoidance).
- **Logs**: Tracks the number of objects detected and the robot’s reactions to them.
- **User Interaction**: Control the robot’s behavior by choosing between Curious, Scaredy, or Any mode. Press 'X' to stop.

## How it Works 🚀
1. When powered on, the robot prompts you to select between:
   - **Curious**: Moves towards objects but adjusts distance if too close.
   - **Scaredy**: Avoids objects and moves away when proximity is too close.
   - **Any**: A mixed mode that combines both behaviors.
   
2. Based on the mode:
   - **Curious Mode**: If an object is detected, the robot moves closer or further away to maintain a specific distance. If no object is found after a set time, it rotates 120° and resumes exploration. After interacting with an object, the robot pauses for 2 seconds before moving on.
   - **Scaredy Mode**: The robot wanders with blue lights. If it detects an object within a specific range, its lights turn red, and it moves away.
   
3. The robot logs every object it detects, ensuring traceability.

## Lights and Indicators 🌈
- **Green**: Object detected (Curious mode).
- **Red**: Danger (Scaredy mode, too close to an object).
- **Blinking**: Moving away (Curious mode).

## Code Breakdown 🧑‍💻
This project is split into four key classes, each handling a different aspect of the SwiftBot’s behavior:

### 1. `MainClass` - Core functionality & decision logic
This class powers up the robot and manages the user’s choice between modes.

\```java
// MainClass snippet
public class MainClass {
   // Initialize robot and prompt mode selection
   // ...
}
\```

### 2. `CuriousClass` - Handles Curious Mode behavior
This class manages object detection in curious mode, controlling movement to maintain the desired distance from objects.

\```java
// CuriousClass snippet
public class CuriousClass {
    // Detect object and adjust distance
    // ...
}
\```

### 3. `ScaredyClass` - Handles Scaredy Mode behavior
In this class, the robot reacts to detected objects by moving away when they get too close.

\```java
// ScaredyClass snippet
public class ScaredyClass {
    // Detect object and initiate avoidance behavior
    // ...
}
\```

### 4. `LogExecutionClass` - Logs object interactions
Tracks the number of objects detected, mode-specific interactions, and outputs this information to the user.

\```java
// LogExecutionClass snippet
public class LogExecutionClass {
    // Log the number of detected objects
    // ...
}
\```

## Screenshots & GIFs 🎥
Here are some visual highlights of the SwiftBot in action. You can see the Eclipse IDE where the code runs and the robot’s behavior.

![Curious Mode GIF](path-to-curious-mode-gif.gif)
*Curious mode detecting and following objects.*

![Scaredy Mode GIF](path-to-scaredy-mode-gif.gif)
*Scaredy mode avoiding objects.*

## Getting Started 🛠️
To get started with SwiftBot, you'll need:
- A Raspberry Pi with sensors and motor control.
- Your choice of IDE (e.g., Eclipse) to work with the code.
- Access to the SwiftBot repository.

Clone the repository:

\```bash
git clone https://github.com/your-username/SwiftBot.git
\```

Run the program:

\```bash
java MainClass
\```

## Future Enhancements 🚧
- Integration with AI for smarter object detection.
- Voice commands to switch between modes.
- Enhanced logging with timestamps and environmental data.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
