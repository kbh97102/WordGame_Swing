package graphics.WordWindow;

import logic.WordManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class WordWindow {
    private JFrame frame;
    private JPanel contentPanel;
    private Vector<String> wordData;
    private static WordWindow wordWindowInstance;
    private JTextArea displayWordTA;
    private JTextField userInputTF;
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton loadButton;
    private JPanel displayPanel;
    private JPanel editPanel;
    private JPanel buttonPanel;
    /**
     * Display wordData and edit
     */
    private WordWindow(){
        init();
        wordData = WordManage.getInstance().getWordData();
        JScrollPane scrollPane = new JScrollPane(displayWordTA);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        displayWordTA.setEditable(false);
        displayWord();
        displayWordTA.setBackground(Color.orange);
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayWordTA, BorderLayout.CENTER);
        displayPanel.add(scrollPane, BorderLayout.EAST);
        addButton.addActionListener(this::setAddButtonEvent);
        deleteButton.addActionListener(this::setDeleteButtonEvent);
        saveButton.addActionListener(event -> WordManage.getInstance().saveWordToFile());
        loadButton.addActionListener(event -> wordData = WordManage.getInstance().getWordData());
        buttonPanel.setLayout(new GridLayout(1,4,5,1));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        editPanel.setLayout(new BorderLayout());
        editPanel.add(userInputTF,BorderLayout.CENTER);
        editPanel.add(buttonPanel,BorderLayout.SOUTH);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(displayPanel, BorderLayout.CENTER);
        contentPanel.add(editPanel, BorderLayout.SOUTH);
        frame.add(contentPanel);
        frame.setTitle("WordEdit");
        frame.setSize(360,640);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public void displayWord(){
        displayWordTA.removeAll();
        for(String word : wordData){
            displayWordTA.append(word);
            displayWordTA.append("\n");
        }
    }
    private void setAddButtonEvent(ActionEvent e){
        String input = userInputTF.getText();
        wordData.add(input);
        userInputTF.setText("");
        displayWord();
    }
    private void setDeleteButtonEvent(ActionEvent e){
        String input = userInputTF.getText();
        if(wordData.contains(input)){
            wordData.remove(input);
            displayWord();
        }
        else{
            JOptionPane.showMessageDialog(deleteButton,"Do not Exist", "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static WordWindow getWordWindowInstance(){
        if(wordWindowInstance == null){
            return wordWindowInstance = new WordWindow();
        }
        return wordWindowInstance;
    }
    private void init(){
        buttonPanel = new JPanel();
        displayPanel = new JPanel();
        editPanel = new JPanel();
        displayWordTA = new JTextArea();
        userInputTF = new JTextField(15);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        frame = new JFrame();
        contentPanel = new JPanel();
    }
}
