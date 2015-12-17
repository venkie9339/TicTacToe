package com.rayan.venki.tictactoe;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class TwoPlayerActivity extends AppCompatActivity {

    public ImageView counter;
    public Toast toast;
    public Context context;
    public Button playAgain;
    public GridLayout gridLayout;

    int player1 = 0; // Player 1
    int player2 = 1; // Player 2
    int count   = 0;

    // Check for current state available
    int state = 0;

    // Individual block # using tag manager
    int[] blocks = {2,2,2,2,2,2,2,2,2};

    int[][] winningStates = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    // Set flag for whether game is over or not
    boolean gameOver = false;
    boolean isGameDraw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_activity);
        context = getApplicationContext();
        playAgain = (Button) findViewById(R.id.button);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
    }

    // Method called when the user tap on any of the block
    public void dropIn(View view) {
        counter = (ImageView) view;

        // Get the current click state from getTag() method
        int blockClicked = Integer.parseInt(counter.getTag().toString());

        // Check for the state against the blocks array
        // If its 2 means user haven't click on that block yet
        if(!gameOver && blocks[blockClicked] == 2) {

            // If successful then set the current state 0/1 depending on the var
            blocks[blockClicked] = state;

            // Animation block
            counter.setTranslationY(-1000f);

            if (state == 1) {
                counter.setImageResource(R.drawable.cross);
                state = 0;
            } else {
                counter.setImageResource(R.drawable.circle);
                state = 1;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(100);

            for(int[] winningState : winningStates) {
                if( (blocks[winningState[0]] == blocks[winningState[1]]) &&
                        (blocks[winningState[1]] ==  blocks[winningState[2]]) &&
                        blocks[winningState[0]] != 2) {

                    isGameDraw = false;

                    // Function call to decide which player won the game
                    decideGame(blocks[winningState[0]]);

                } else {

                    isGameDraw = true;

                }
            }

            count = count + 1;
        }

        // If the game is draw and none of the blocks are empty.
        // Call Decide game for draw.
        if(isGameDraw && count == blocks.length) {
            decideGame(3);
        }
    }

    public void decideGame(int state) {

        String msg;
        if(state == player1) {
            msg = "Player 1 has won the game";
        } else if(state == player2) {
            msg = "Player 2 has won the game";
        } else {
            msg = "Please try again";
        }
        gameOver = true;
        toast = Toast.makeText(context,msg, Toast.LENGTH_LONG);
        toast.show();
        playAgain.setVisibility(View.VISIBLE);

    }

    // Method for resetting game
    public void playAgain(View view) {
        state = 0;
        count = 0;
        gameOver = false;
        isGameDraw = false;
        playAgain.setVisibility(View.INVISIBLE);

        // Resetting the blocks back to 2
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = 2;
        }

        // Looping throught childs in the grid and setting them to 0
        // which means remove image resource from the each and every row and column
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }
}
