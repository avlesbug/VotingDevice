import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class VotingDevice implements ActionListener {
    int pollId;
    JFrame frame;
    JPanel panel;
    JTextField input;
    JLabel question;
    JButton opt1Button;
    JButton opt2Button;
    JButton sendButton;
    JButton changePollButton;
    SimplePoll poll;
    HttpImpl httpImpl;
    ArrayList<Answer> votes;

    public VotingDevice(int pollId){
        input = new JTextField();
        httpImpl = new HttpImpl();
        votes = new ArrayList<>();
        this.pollId = pollId;
        try {
            poll = httpImpl.sendGET(pollId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame = new JFrame();
        panel = new JPanel();

        question = new JLabel(poll.getQuestion());
        opt1Button = new JButton();
        opt1Button.setText(poll.getOpt1().toString());
        opt1Button.addActionListener(this);
        opt2Button = new JButton();
        opt2Button.setText(poll.getOpt2().toString());
        opt2Button.addActionListener(this);
        sendButton = new JButton();
        sendButton.setText("Send result");
        sendButton.addActionListener(this);
        changePollButton = new JButton();
        changePollButton.setText("Change poll");
        changePollButton.addActionListener(this);

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new GridLayout(0,1));
        panel.add(question);
        panel.add(opt1Button);
        panel.add(opt2Button);
        panel.add(sendButton);
        panel.add(input);
        panel.add(changePollButton);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Voting Device");
        frame.pack();
        frame.setVisible(true);


    }

    // Må initieres med id'en til en eksisterende poll
    public static void main(String[] args) {
        new VotingDevice(151);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == opt1Button){
            votes.add(poll.getOpt1());
            System.out.println("You voted " + opt1Button.getText());
        } else if(e.getSource() == opt2Button){
            votes.add(poll.getOpt2());
            System.out.println("You voted " + opt2Button.getText());
        } else if(e.getSource() == sendButton){
            System.out.println("Sending result to the cloud!");
            try {
                httpImpl.sendPOST(pollId,votes);
                votes = new ArrayList<>();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else if(e.getSource() == changePollButton){
            int newId = Integer.parseInt(input.getText());
            try {
                poll = httpImpl.sendGET(newId);
                question.setText(poll.getQuestion());
                pollId = newId;
                votes = new ArrayList<>();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
}
