## Code Review

Reviewed by: Morris Benjamin, u7616055

Reviewing code written by: tong yuan xiong, u7649318

Component: Marrakech class

```java
/**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        //FIXME: Task 9

        // Verify that the input is valid
        if (currentAssam.length() != 4 || rotation % 90 != 0 ) {
            return currentAssam; // get back the currentAssam
        }

        // get the current Assam direction
        char currentDirection = currentAssam.charAt(3);

        // def the new direction
        char newDirection;

        // Calculate the new orientation based on the rotation angle
        if (rotation == 90) {
            switch (currentDirection) {
                case 'N':
                    newDirection = 'E'; // From North to East
                    break;
                case 'E':
                    newDirection = 'S'; // From East to South
                    break;
                case 'S':
                    newDirection = 'W'; // From South to West
                    break;
                case 'W':
                    newDirection = 'N'; // From West to North
                    break;
                default:
                    return currentAssam;
            }
        } else if (rotation == 270) {
            // Counterclockwise rotation is equal to three clockwise rotations.
            for (int i = 0; i < 3; i++) {
                currentAssam = rotateAssam(currentAssam, 90);
            }
            return currentAssam;
        } else {
            return currentAssam; // Unsupported rotation angle, return to original state
        }

        // Building new Assam strings
        return currentAssam.substring(0,3)+newDirection;
    }

### Comments 
**Functionality:
Provided a java class that rotates assam based on the direction the direction input (90* or 270, left or right)

- The currentAssam length check makes sure that it takes in the correct values


**Readability:
The rotateAssam method includes comments describing the purpose of the method. Good documentation is crucial for understanding the code's purpose and usage

**Encapsulation:

**Error Handling:
The code includes error handling for invalid input. For example, it checks for if there is an incorrect assam string.

**What are the best features of this code?**
How clear and concise it is

**Is the code well-documented?**
The code has many comments explaining it all.

**Does it follow Java code conventions, and is the style consistent throughout?**
The code follows Java code conventions, and the style is consistent throughout

**Suggestions for Improvement:**
