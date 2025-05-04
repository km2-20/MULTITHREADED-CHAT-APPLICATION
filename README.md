# MULTITHREADED-CHAT-APPLICATION

*NAME*: KALYANI SITARAM MAHAJAN

*COMPANY*: CODTECH IT SOLUTIONS

*INTERN ID*: CT04DA245

*DOMAIN*: JAVA PROGRAMMING

*DURATION*: 4 WEEKS

*MENTOR*: NEELA SANTOSH

*OUTPUT*:
![Image](https://github.com/user-attachments/assets/5d42c5ed-297f-4c9a-a5c3-48436b18a0a3)

![Image](https://github.com/user-attachments/assets/9aaa808a-0457-418f-b03c-5237f62cab8d)

*DESCRIPTION*:
The project implements a basic Java-based client-server chat system using sockets and Swing for the user interface. It allows one client to connect to a server, exchange messages in real-time, and play a sound on message receipt using an MP3 file.

1. *Server Side:*
The server initializes a GUI with a chat area and input field. It waits for a client connection on port 1111, sets up input/output streams, and spawns a thread to continuously read messages from the client. Upon receiving a message, it updates the GUI and plays a notification sound.

2. *Client Side:*
The client prompts the user for the server’s IP, connects to the server, and sets up a GUI. It sends messages typed by the user and starts a thread to listen for incoming messages. Each received message is displayed and accompanied by a sound.

3. *Output:*
After connection, the client sends a message, which the server displays and confirms with a sound. The server replies, which is shown in the client window along with its own notification sound. Both sides can continue chatting interactively.

The application is easy to use, with real-time messaging and audio feedback. However, it supports only one client, has minimal error handling, and plays sound in a blocking manner, which may freeze the UI briefly.
This code serves as a solid introduction to Java networking and GUI design. It’s suitable for learning socket programming, though it would benefit from scalability improvements, better threading for sound, and enhanced error handling.


