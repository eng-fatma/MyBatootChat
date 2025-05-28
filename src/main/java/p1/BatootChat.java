package p1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class BatootChat {
    private JFrame frame;// شكل تبعي
    private JTextArea chatArea;// مكان الكلام 
    private JTextField inputField;// المكان الي مستخدم بيتكلم فيه
    private JButton sendButton;// ارسال
    private int step = -1;// عشان الغه رجعنها وره 
    private String userName = "";// خزن
    private int userAge = 0;// خزن
    private String userJob = "";// خزن
    private boolean isEnglish = false;// نحدد المحادثه بلانجليزي او بلعربي
    public BatootChat() {
        frame = new JFrame("محادثة مع بطوط");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);//ممنوع المستخدم  يكتب فيها لانها تبع بطوط بس
        chatArea.setFont(new Font("Arial", Font.BOLD, 15));
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);// عشان لو رط بحصل شريط تمرير علي جنب 

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("إرسال");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput();
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput();
            }
        });

        frame.setVisible(true);
    }
    // نبدا بقا في محور القصه 

    private void handleUserInput() {
        String userText = inputField.getText().trim();// trim دي بيحذف اي مسافات 
        inputField.setText("");// نفضي مكان المستخدم بعد ما يبعت 

        if (userText.isEmpty()) // عشن لو مستخدم مش دخل شي ما يصير اي شي 
        {
            return;
        }

        chatArea.append(userText + "\n");
        /*
        userText.matches("[A-Za-z0-9\\s]+") دي الجملة السحرية اللي بتحدد اللغة
matches() دي بتشوف هل النص اللي كتبه المستخدم بيتوافق مع النمط ده ولا لأ
  التعبير [A-Za-z0-9\\s]+:

[A-Za-z]  يعني أي حرف إنجليزي كبير أو صغير.
[0-9]  يعني أي رقم.
\\s يعني مسافة فارغة (Space).
+  يعني الكلام لازم يكون فيه حرف واحد على الأقل من دول.
 بمعنى؟

لو المستخدم كتب "Hello" أو "123" أو "Hi 45"  الماتش يحصل، يعني المستخدم بيتكلم إنجليزي  isEnglish = true
لو المستخدم كتب "مرحبا" أو "أنا هنا" أو "٣٥"  الماتش ما يحصلش، يعني المستخدم بيتكلم عربي  isEnglish = false

        chatArea.append(isEnglish ? "Hello! What is your name?\n" : "مرحبًا! كيف يمكنني أن أناديك؟\n");
 معناه إيه؟

لو isEnglish = true يعني المستخدم بيتكلم إنجليزي  بطوط يرد بـ "Hello! What is your name?"
لو isEnglish = false يعني المستخدم بيتكلم عربي  بطوط يرد بـ "مرحبًا! كيف يمكنني أن أناديك؟"
         */

        if (step == -1)// نحدد الغه
        {
            isEnglish = userText.matches("[A-Za-z0-9\\s]+");// معلومه كنز 
            chatArea.append(isEnglish ? "Hello! What is your name?\n" : "مرحبًا! كيف يمكنني أن أناديك؟\n");
            step = 0;
            return;
        }

        switch (step) {
            case 0:
                userName = userText;
                chatArea.append(isEnglish ? " Nice to meet you, " + userName + "! How old are you?\n" : " أهلاً " + userName + "! كم عمرك؟\n");
                step++;
                break;
            case 1:
                try {
                    userAge = Integer.parseInt(userText);
                    chatArea.append(isEnglish ? " Great! What is your job?\n" : " رائع! ما هي وظيفتك؟\n");
                    step++;
                } catch (NumberFormatException e) {
                    chatArea.append(isEnglish ? " Please enter a valid number for your age!\n" : " من فضلك أدخل عمرك كرقم صحيح!\n");
                }
                break;
            case 2:
                userJob = userText;
                displayFinalMessage();
                step++;
                break;
            default:
                chatArea.append(isEnglish ? " We have finished chatting! Thank you, " + userName + "!\n" : " لقد أكملنا الدردشة! شكراً لك " + userName + "!\n");
                break;
        }
    }

    private void displayFinalMessage() {
        String message = isEnglish
                ? "Batoot: Nice to meet you, " + userName + "! You are " + userAge + " years old and work as " + userJob + ".\n"
                : "بطوط: أهلاً " + userName + "! عمرك " + userAge + " سنة وتعمل كـ " + userJob + ".\n";

        if (userAge < 18) {
            message += isEnglish
                    ? "It looks like you're young, enjoy your time and learn new things!\n"
                    : "يبدو أنك صغير، استمتع بوقتك وتعلم أشياء جديدة!\n";
        } else if (userAge < 30) {
            message += isEnglish
                    ? "Youth is full of challenges, make the most of it!\n"
                    : "مرحلة الشباب مليئة بالتحديات، استغلها جيدًا!\n";
        } else {
            message += isEnglish
                    ? "You have good life experience, share it with others!\n"
                    : "لديك خبرة جيدة في الحياة، شاركها مع الآخرين!\n";
        }
        chatArea.append(message);
    }

    public static void main(String[] args) {
        new BatootChat();
    }
}
