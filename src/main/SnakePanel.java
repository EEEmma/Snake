package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {
    //加载图片
    ImageIcon head_up=new ImageIcon("src/resource/head_up.png");
    ImageIcon head_down=new ImageIcon("src/resource/head_down.png");
    ImageIcon head_left=new ImageIcon("src/resource/head_left.png");
    ImageIcon head_right=new ImageIcon("src/resource/head_right.png");
    ImageIcon title=new ImageIcon("src/resource/title.jpg");
    ImageIcon body=new ImageIcon("src/resource/body.png");
    ImageIcon food=new ImageIcon("src/resource/food.png");

    //蛇的数据结构
    int[]snakeX=new int[800];
    int[]snakeY=new int[800];
    int len=3;//蛇的初始长度
    String direction="R";//初始向右
    boolean isStarted=false;
    boolean isFailed=false;
    int score=0;
    //食物
    Random r=new Random();
    int foodX=r.nextInt(34)*25+25;
    int foodY=r.nextInt(24)*25+75;
    Timer timer=new Timer(150,this);

    public SnakePanel(){
        this.setFocusable(true);
        initSnake();//放置静态蛇
        this.addKeyListener(this);//添加监听
        timer.start();//打开定时器
    }
    //初始化蛇
    public void initSnake(){
        len=3;
        score=0;
        direction="R";
        snakeX[0]=100;
        snakeY[0]=100;
        snakeX[1]=75;
        snakeY[1]=100;
        snakeX[2]=50;
        snakeY[2]=100;
        isStarted=false;
        isFailed=false;
    }
    public void paint(Graphics graphic) {

        //设置画布背景颜色
        this.setBackground(Color.BLACK);
        graphic.fillRect(25,75,850,600);
        //设置标题
        title.paintIcon(this,graphic,25,11);
        //画蛇头
        if(direction.equals("R")){
            head_right.paintIcon(this,graphic,snakeX[0],snakeY[0]);
        }else if(direction.equals("L")){
            head_left.paintIcon(this,graphic,snakeX[0],snakeY[0]);
        }else if(direction.equals("U")){
            head_up.paintIcon(this,graphic,snakeX[0],snakeY[0]);
        }else if(direction.equals("D")){
            head_down.paintIcon(this,graphic,snakeX[0],snakeY[0]);
        }
        //画蛇身
        for(int i=1;i<len;i++){
            body.paintIcon(this,graphic,snakeX[i],snakeY[i]);
        }
        //画开始/结束提示语
        if(!isStarted){
            graphic.setColor(Color.PINK);
            graphic.setFont(new Font("arial",Font.BOLD,20));
            graphic.drawString("Press Space to Start/Palse",320,300);
        }
        if(isFailed){
            graphic.setColor(Color.PINK);
            graphic.setFont(new Font("arial",Font.BOLD,20));
            graphic.drawString("GAME OVER!!!Press Space to Start/Palse",260 ,300);
            graphic.drawString("Your final score is "+score,300 ,300);
        }
        //画食物
        food.paintIcon(this,graphic,foodX,foodY);
        //画分数和长度的统计
        graphic.setColor(Color.PINK);
        graphic.setFont(new Font("arial",Font.BOLD,20));
        graphic.drawString("SCORE:"+score,750,35);
        graphic.drawString("LENGTH:"+len,750,55);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
     int keyCode=e.getKeyCode();
     if(keyCode==KeyEvent.VK_SPACE){
         //画失败提示语
         if(isFailed){
            initSnake();
         }else{
             isStarted=!isStarted;
         }

       //  repaint();
     } else if(keyCode==KeyEvent.VK_LEFT&&!direction.equals("R")){
         direction="L";
     }else if(keyCode==KeyEvent.VK_RIGHT&&!direction.equals("L")){
         direction="R";
     }else if(keyCode==KeyEvent.VK_UP&&!direction.equals("D")){
         direction="U";
     }else if(keyCode==KeyEvent.VK_DOWN&&!direction.equals("U")){
         direction="D";
     }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //1.重新定闹钟
        //2.蛇移动
        //3.画布重画
        timer.start();
        if(isStarted&&!isFailed){
            //调整身体位置
            for(int i=len;i>0;i--){
                snakeX[i]=snakeX[i-1];
                snakeY[i]=snakeY[i-1];
            }
            //调整头位置
            if(direction.equals("R")){
                snakeX[0]+=25;
                if(snakeX[0]>850){snakeX[0]=25;}
            }else if(direction.equals("L")){
                snakeX[0]-=25;
                if(snakeX[0]<25){snakeX[0]=850;}
            }else if(direction.equals("U")){
                snakeY[0]-=25;
                if(snakeY[0]<75){snakeY[0]=675;}
            }else if(direction.equals("D")){
                snakeY[0]+=25;
                if(snakeY[0]>650){snakeY[0]=75;}
            }
            //吃食物的逻辑
            if(snakeX[0]==foodX&&snakeY[0]==foodY){
                len++;
                score++;
                foodX=r.nextInt(34)*25+25;
                foodY=r.nextInt(24)*25+75;
            }
            for(int i=1;i<len;i++){
                if(snakeX[i]==snakeX[0]&&snakeY[i]==snakeY[0]){
                    isFailed=true;
                }

            }

        }
        repaint();

    }
}


