package Calculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Stack;

class MyException extends Exception {
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }
}

public class MyCalculator implements ActionListener {
    private JTextField textField;    //输入文本
    private JTextField outputField;  //结果文本
    private String input;            //结果
    private String lastInput;        //上一个式子
    private String Sum = "";          //计算求和结果
    private int temp = 0;              //数字的长度
    String LastActionCommand = "";
    String bt[] = {"AC", "←", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="};


    public MyCalculator() {
        input = "";
        JFrame frame = new JFrame("计算器");

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 50));
        textField.setBackground(new Color(246, 242, 238));
        textField.setBorder(null);                            //去边框
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setFont(new Font("weiruanyahei", Font.BOLD, 50));
        textField.setEditable(false);                         //不可编辑

        outputField = new JTextField();
        outputField.setPreferredSize(new Dimension(500,50));
        outputField.setBackground(new Color(246, 242, 238));
        outputField.setBorder(null);                            //去边框
        outputField.setHorizontalAlignment(JTextField.RIGHT);
        outputField.setFont(new Font("weiruanyahei", Font.PLAIN, 25));
        outputField.setForeground(new Color(134, 134, 134));
        outputField.setEditable(false);                         //不可编辑

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2,1));
        p2.setBorder(new EmptyBorder(10, 10, 10, 10));
        p2.setBackground(new Color(246, 242, 238));
        p2.add(outputField);
        p2.add(textField);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(5, 4, 8, 8));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.setBackground(new Color(246, 242, 238));
        for (int i = 0; i < 20; i++) {
            JButton button = new JButton(bt[i]);
            p.add(button);
            button.setBackground(new Color(255, 255, 255));
            button.setFont(new Font("weiruanyahei", Font.PLAIN, 35));
            button.setFocusPainted(false);                        //去焦点
            button.setBorderPainted(false);                       //去边框
            button.addActionListener(this);
        }

        frame.getContentPane().add(p2, BorderLayout.NORTH);
        frame.getContentPane().add(p, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int cnt = 0;
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("+") || actionCommand.equals("-") ||
                actionCommand.equals("×") || actionCommand.equals("÷")) {
            if (LastActionCommand.equals("+") || LastActionCommand.equals("-") ||
                    LastActionCommand.equals("×") || LastActionCommand.equals("÷")) {
                input = input.substring(0, input.length() - 3);
            }
            input += " " + actionCommand + " ";
            LastActionCommand = actionCommand;
            temp = 0;
        } else if (actionCommand.equals("AC")) {
            input = "";
            lastInput = "";
            temp = 0;
        } else if (actionCommand.equals("←")) {     //此按键有待优化
            if (input.length() != 0)
                input = input.substring(0, input.length() - 1);
            temp--;
        } else if (actionCommand.equals("%")) {
            input += " " + actionCommand + " ";
            temp = 0;
        } else if (actionCommand.equals("+/-")) {
            StringBuffer buf = new StringBuffer(input);
            buf.insert(input.length() - temp, "-");
            input = String.valueOf(buf);
        } else if (actionCommand.equals("=")) {
            try {
                lastInput = input + "=";
                input = calculator(input);
            } catch (MyException e1) {
                input = e1.getMessage();
            }
            textField.setText(input);
            outputField.setText(lastInput);
            /*input = Sum;*/
            temp = 0;
            cnt = 1;
        } else {
            input += actionCommand;
            temp = temp + 1;
            LastActionCommand = "   ";
        }
        if (cnt == 0) {
            textField.setText(input);
            outputField.setText(lastInput);
        }
    }

    private String calculator(String input) throws MyException {    //运算方法
        String[] comput = input.split(" ");
        Stack<Double> stack = new Stack<>();
        Double m = Double.parseDouble(comput[0]);
        stack.push(m);                                    //将第一个数字入栈

        for (int i = 1; i < comput.length; i++) {
            if (i % 2 == 1) {
                if (comput[i].equals("+"))
                    stack.push(Double.parseDouble(comput[i + 1]));
                if (comput[i].equals("-"))
                    stack.push(-Double.parseDouble(comput[i + 1]));
                if (comput[i].equals("×")) {                //将前一个数出栈做乘法再入栈
                    Double d = stack.peek();                //取栈顶元素
                    stack.pop();                            //删除栈顶元素
                    stack.push(DoubleMul(d, Double.parseDouble(comput[i + 1])));//已避免精度问题
                }
                if (comput[i].equals("÷")) {
                    double help = Double.parseDouble(comput[i + 1]);
                    if (help == 0)
                        throw new MyException("错误");
                    double d = stack.peek();
                    stack.pop();
                    stack.push(DoubleDiv(d, help));
                }
                if (comput[i].equals("%")) {
                    double d = stack.peek();
                    stack.pop();
                    stack.push(DoubleDiv(d, 100));
                }
                if (comput[i].equals("+/-")) {
                    double d = stack.peek();
                    stack.pop();
                    stack.push(-d);
                }
            }
        }
        double sum = 0;
        while (!stack.isEmpty()) {
            sum = DoubleSum(sum, stack.peek());        //调用函数计算，避免精度问题
            stack.pop();
        }
        Sum = String.valueOf(sum);
        if ((int) sum - sum == 0)
            Sum = Sum.substring(0, Sum.length() - 2);
        return Sum;
    }

    public static void main(String[] args) {
        MyCalculator MC = new MyCalculator();
    }


    //解决double运算时精度的问题的运算函数
    //加法
    public double DoubleSum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    //乘法
    public double DoubleMul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    //除法
    public double DoubleDiv(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        int scale = 100;                //保留小数点位数
        return bd1.divide
                (bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}