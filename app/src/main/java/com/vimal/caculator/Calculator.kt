package com.vimal.caculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.idapgroup.autosizetext.AutoSizeText

// List of buttons
val buttonList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "AC", "0", ".", "="
)

@Composable
fun Calculator(modifier: Modifier = Modifier, viewModel: CalculatorViewModel) {
    val equationText = viewModel.equation.observeAsState()
    val resultText = viewModel.result.observeAsState()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End,
        ) {
            AutoSizeText(
                text = equationText.value ?: "",
                fontSize = 30.sp,
                minFontSize = 12.sp,
                textAlign = TextAlign.End,
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            AutoSizeText(
                text = resultText.value ?: "",
                fontSize = 60.sp,
                minFontSize = 30.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Grid of buttons
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
            ) {
                itemsIndexed(buttonList) { index, btn ->
                    CalculatorButton(
                        btn = btn,
                        onClick = {
                            viewModel.onButtonClick(btn)
                        }
                    )
                }
            }
        }
    }
}

// Calculator Buttons
@Composable
fun CalculatorButton(btn: String, onClick: () -> Unit) {
    Box(modifier = Modifier
        .padding(8.dp)
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(80.dp),
            shape = RectangleShape,
            containerColor = getColor(btn),
            contentColor = Color.White,
        ) {
            Text(
                text = btn,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

fun getColor(btn: String): Color {
    if(btn == "C" || btn == "AC")
        return Color(0xFFDC7633)
    if(btn == "+" || btn == "-" || btn == "*" || btn == "/")
        return Color(0xFF5499c7)
    if(btn == "=")
        return Color(0xFFd98880)
    if(btn == "(" || btn == ")")
        return Color(0xFF5D6D7E)
    return Color(0xFF515a5a)
}