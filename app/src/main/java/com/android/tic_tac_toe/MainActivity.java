package com.android.tic_tac_toe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {


    //    0:red 1:yellow 2:empty
    private int activePlayer = 0;
    int size=3;


    private int[] placesFilled = new int[size*size];
    private int[][] winningPositions= {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private boolean winner=false;


    private View boardG;
    private TextView winnerBoard;
    private Button resetGame;

    //Initializes or resets the UI and other components to default
    @SuppressLint("SetTextI18n")
    private void initializeUI(){

        GridLayout board=(GridLayout)boardG;
        for(int i=0;i<board.getChildCount();i++){
            ImageView place = (ImageView)board.getChildAt(i);
            place.setImageDrawable(null);
        }

        animateGridLayout(1,1,600);


        winnerBoard.setText("");


        setResetGameAway(0);
        bringResetGameBack(1000,1800);
        resetGame.setText("Reset Game");



        activePlayer = 0;


        for(int i=0;i<size*size;i++){
            placesFilled[i]=2;
        }

        winner=false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        winnerBoard=(TextView)findViewById(R.id.winnerBoard);
        boardG=findViewById(R.id.board);
        resetGame=(Button)findViewById(R.id.resetGame);
        initializeUI();
    }


    public void onReset(View v){
        initializeUI();
    }



    @SuppressLint("SetTextI18n")
    public void onClick(View view){
        ImageView pos=(ImageView)view;
        int tag=Integer.parseInt(view.getTag().toString());
        if(conditionHideButton()) {
            setResetGameAway(1000);
            bringResetGameBack(-1000, 800);
        }
        if(placesFilled[tag]==2&&!winner) {
            pos.setTranslationY(-3000);
            if (activePlayer == 0) {
                pos.setImageResource(R.drawable.red);
                placesFilled[tag] = activePlayer;
                activePlayer = 1;
            } else if (activePlayer == 1) {
                pos.setImageResource(R.drawable.yellow);
                placesFilled[tag] = activePlayer;
                activePlayer = 0;
            }
            pos.animate().translationYBy(3000).setDuration(300);
            for(int[] winningPosition : winningPositions){
                if(placesFilled[winningPosition[0]]==placesFilled[winningPosition[1]]&&placesFilled[winningPosition[1]]==placesFilled[winningPosition[2]]&&placesFilled[winningPosition[0]]!=2){
                    String winnerColor="";
                    if(placesFilled[winningPosition[0]]==0)
                        winnerColor="Player:Red won the match!";
                    else if(placesFilled[winningPosition[0]]==1)
                        winnerColor="Player:Yellow won the match!";
                    setWinnerBoardAway(-1000);
                    winnerBoard.setText(winnerColor);
                    bringWinnerBoardBack(1000,500);
                    winner=true;
                    setResetGameAway(1000);
                    resetGame.setText("play again");
                    bringResetGameBack(-1000,800);
                }
                else if(!winner&&conditionDraw()){
                    setWinnerBoardAway(-1000);
                    winnerBoard.setText("Match is Draw!");
                    bringWinnerBoardBack(1000,500);
                    setResetGameAway(1000);
                    resetGame.setText("play again");
                    bringResetGameBack(-1000,800);
                }
            }
        }
    }

    private void setWinnerBoardAway(float val){
        winnerBoard.setTranslationY(val);
    }
    private void bringWinnerBoardBack(float val,long duration){
        winnerBoard.animate().translationYBy(val).setDuration(duration);
    }

    private void setResetGameAway(float val){
        resetGame.setTranslationY(val);
    }
    private void bringResetGameBack(float val,long duration){
        resetGame.animate().translationYBy(val).setDuration(duration);
    }

    private boolean conditionDraw(){
        for(int i:placesFilled){
            if(i==2){
                return false;
            }
        }
        return true;
    }
    private boolean conditionHideButton(){
        int counter=0;
        for(int i:placesFilled){
            if(i!=2)
                counter++;
        }
        return counter == 0;
    }

    private void animateGridLayout(float scaleX,float scaleY,long duration){
        GridLayout board=(GridLayout)boardG;
        board.setScaleX(0);
        board.setScaleY(0);
        board.setAlpha(0);
        board.animate().scaleX(scaleX).scaleY(scaleY).alpha(1).setDuration(duration);
    }
}