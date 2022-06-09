package com.monkeealert.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

  private List<String> signs = Arrays.asList("✊", "✋", "✌️");
  private boolean hasGameStarted = false;
  private int wins = 0;
  private int loses = 0;

  private TextView computerSign;
  private TextView playerSign;

  private Button rockButton;
  private Button paperButton;
  private Button scissorsButton;

  private TextView winsText;
  private TextView losesText;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    computerSign = findViewById(R.id.computerSign);
    playerSign = findViewById(R.id.playerSign);

    rockButton = findViewById(R.id.rockButton);
    paperButton = findViewById(R.id.paperButton);
    scissorsButton = findViewById(R.id.scissorsButton);

    winsText = findViewById(R.id.winsText);
    losesText = findViewById(R.id.losesText);

    playerSign.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {}

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {
        if(hasGameStarted) {
          setComputerSign();
        }
      }

      @Override
      public void afterTextChanged(Editable editable)
      {}
    });

    rockButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        hasGameStarted = true;
        playerSign.setText(rockButton.getText());
      }
    });

    paperButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view) {
        hasGameStarted = true;
        playerSign.setText(paperButton.getText());
      }
    });

    scissorsButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view) {
        hasGameStarted = true;
        playerSign.setText(scissorsButton.getText());
      }
    });
  }

  private void setComputerSign()
  {
    Random random = new Random();
    String choice = signs.get(random.nextInt(signs.size()));

    computerSign.setText(choice);
    checkChoices();
  }

  private void checkChoices()
  {
    String computer = computerSign.getText().toString();
    String player = playerSign.getText().toString();

    boolean win = compareEmojis(computer, "✊") && compareEmojis(player, "✋")
            || compareEmojis(computer, "✋") && compareEmojis(player, "✌️")
            || compareEmojis(computer, "✌️") && compareEmojis(player, "✊");

    boolean lose = compareEmojis(computer, "✋") && compareEmojis(player, "✊")
            || compareEmojis(computer, "✌️") && compareEmojis(player, "✋")
            || compareEmojis(computer, "✊") && compareEmojis(player, "✌️");

    if(win) {
      wins++;
      winsText.setText("Wins: " + wins);
    } else if (lose) {
      loses++;
      losesText.setText("Loses: " + loses);
    }
  }

  private boolean compareEmojis(String emoji, String compareTo)
  {
    byte[] emojiBytes = emoji.getBytes(StandardCharsets.UTF_8);
    byte[] compareToBytes = compareTo.getBytes(StandardCharsets.UTF_8);
    boolean isSame = false;

    if(emojiBytes.length == compareToBytes.length) {
      for (int i = 0; i < emojiBytes.length; i++) {
        if(emojiBytes[i] == compareToBytes[i]) {
          isSame = true;
        } else {
          return false;
        }
      }
    }

    return isSame;
  }
}