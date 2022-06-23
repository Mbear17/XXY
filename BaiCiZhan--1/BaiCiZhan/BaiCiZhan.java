package BaiCiZhan;

import JDBCUtils.JDBCUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaiCiZhan implements ActionListener {
    private JTextField textField1;  //题目
    private JTextField textField2;  //对/错
    private JTextField textField3;  //计数
    private String input1;
    private String input2;
    private String input3;
    private JButton button[] = new JButton[4];
    private String buttonWord[] = new String[4];   //选项文字
    private JButton nextButton;
    private String answer;
    int temp = 1;             //题号
    int num = 0;             //答对题数
    question q = new question();
    static BaiCiZhan BCZ = new BaiCiZhan();

    public void MyBaiCiZhan() {
        input3 = new String("已答对题数：" + num + "/20");

        JFrame frame = new JFrame("百词斩");

        textField1 = new JTextField();
        textField1.setEditable(false);
        textField1.setBorder(null);
        textField1.setBackground(new Color(246, 242, 238));
        textField1.setFont(new Font("weiruanyahei", Font.BOLD, 30));
        textField1.setPreferredSize(new Dimension(300, 50));
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textField2 = new JTextField();
        textField2.setEditable(false);
        textField2.setBorder(null);
        textField2.setBackground(new Color(246, 242, 238));
        textField2.setFont(new Font("weiruanyahei", Font.BOLD, 30));
        textField2.setPreferredSize(new Dimension(300, 50));
        textField2.setHorizontalAlignment(JTextField.LEFT);
        textField3 = new JTextField();
        textField3.setEditable(false);
        textField3.setBorder(null);
        textField3.setBackground(new Color(246, 242, 238));
        textField3.setFont(new Font("weiruanyahei", Font.BOLD, 25));
        textField3.setPreferredSize(new Dimension(400, 50));
        textField3.setHorizontalAlignment(JTextField.RIGHT);
        JPanel p3 = new JPanel();
        p3.setPreferredSize(new Dimension(100,50));
        p3.setBackground(new Color(246, 242, 238));
        p3.add(textField3);

        nextButton = new JButton("下一题");
        nextButton.setPreferredSize(new Dimension(100,50));
        nextButton.setBackground(new Color(255, 255, 255));
        nextButton.setFont(new Font("weiruanyahei", Font.PLAIN, 22));
        nextButton.setFocusPainted(false);                        //去焦点
        nextButton.setBorderPainted(false);                       //去边框
        nextButton.addActionListener(this);
        JPanel nextP = new JPanel();
        nextP.setPreferredSize(new Dimension(100,80));
        nextP.setBackground(new Color(246, 242, 238));
        nextP.add(nextButton);


        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6,1,8,8));
        p.setBorder(new EmptyBorder(10,50,10,50));
        p.setBackground(new Color(246, 242, 238));
        p.add(textField1);
        for (int i = 0; i < 4; i++) {
            button[i] = new JButton(buttonWord[i]);
            p.add(button[i]);
            button[i].setBackground(new Color(255, 255, 255));
            button[i].setFont(new Font("weiruanyahei", Font.PLAIN, 35));
            button[i].setFocusPainted(false);                        //去焦点
            button[i].setBorderPainted(false);                       //去边框
            button[i].addActionListener(this);
        }
        p.add(textField2);

        frame.getContentPane().add(p3,BorderLayout.NORTH);
        frame.getContentPane().add(p, BorderLayout.CENTER);
        frame.getContentPane().add(nextP,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setVisible(true);

        textField1.setText(input1);
        textField3.setText(input3);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals(answer)){
            input2 = "回答正确！";
            for (int i = 0; i < 4; i++) {  //答对变成绿色
                if (e.getSource()==button[i]){
                    if (buttonWord[i].equals(answer))
                        button[i].setBackground(new Color(162, 255, 181));
                }
                button[i].setEnabled(false);     //所有选项失效
            }
            num++;
            input3 = "已答对题数：" + num + "/20";
            textField2.setForeground(new Color(162, 255, 181));
            textField2.setText(input2);
            textField3.setText(input3);
        }
        else {
            if (actionCommand.equals("下一题")) {
                temp++;
                if (temp>20){   //弹出提示框
                    JOptionPane.showMessageDialog(null, "你已完成答题，共答对"+num+"题", "提示",JOptionPane.PLAIN_MESSAGE);
                }
                BCZ.getText(temp);
                for (int i = 0; i < 4; i++) {
                    button[i].setText(buttonWord[i]);
                }
                textField1.setText(input1);
                textField2.setText("");
                for (int i = 0; i < 4; i++) {  //颜色初始化，恢复按钮有效
                    button[i].setBackground(new Color(255,255,255));
                    button[i].setEnabled(true);
                }

            } else {
                input2 = "回答错误！";
                for (int i = 0; i < 4; i++) {  //答错变成红色
                    if (e.getSource()==button[i]){
                        button[i].setBackground(new Color(255, 171, 171));
                    }
                    button[i].setEnabled(false);   //所有选项失效
                }
                for (int i = 0; i < 4; i++) {  //对的答案变成绿色
                    if (buttonWord[i].equals(answer))
                        button[i].setBackground(new Color(162, 255, 181));
                }
                textField2.setForeground(new Color(255, 171, 171));
                textField2.setText(input2);
            }
        }
    }







    public static void main(String[] args) {
        BCZ.getText(BCZ.temp);
        BCZ.MyBaiCiZhan();
    }


    public void getText(int i){  //从数据库中获取这一题的数据
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int j = i;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,English,Chinese1,Chinese2,Chinese3,Chinese4,Answer from question where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,j);   //填充占位符
            //执行并返回结果集
            resultSet = preparedStatement.executeQuery();
            //处理结果集
            if (resultSet.next()){//判断结果集的下一条是否有数据，如果有返回true并指针下移，没有则返回false
                //获取当前这条数据集的各字段的值
                int id = resultSet.getInt(1);
                String English = resultSet.getString(2);
                String Chinese1 = resultSet.getString(3);
                String Chinese2 = resultSet.getString(4);
                String Chinese3 = resultSet.getString(5);
                String Chinese4 = resultSet.getString(6);
                String Answer = resultSet.getString(7);
                //将数据封装为一个对象
                q = new question(id, English, Chinese1, Chinese2, Chinese3, Chinese4,Answer);
                input1 = temp+". "+q.getEnglish();
                buttonWord[0] = q.getChinese1();
                buttonWord[1] = q.getChinese2();
                buttonWord[2] = q.getChinese3();
                buttonWord[3] = q.getChinese4();
                answer = q.getAnswer();
                System.out.println(q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }
    }

}
