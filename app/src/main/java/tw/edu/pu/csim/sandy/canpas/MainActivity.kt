package tw.edu.pu.csim.sandy.canpas

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import tw.edu.pu.csim.sandy.canpas.ui.theme.CanpasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanpasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CanvaScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }
        }
    }
}

@Composable
fun CanvaScreen(modifier: Modifier = Modifier) {
    var CanvasWidth by remember { mutableStateOf(0f) }
    var CanvaHeight by remember { mutableStateOf(0f) }
    var minionsX by remember { mutableStateOf(0f) }
    var minionsY by remember { mutableStateOf(0f) }
    var gameOver by remember { mutableStateOf(false) }
    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(id = R.drawable.minions)
    LaunchedEffect(key1 = gameOver) {
        while (!gameOver) {
            delay(timeMillis = 100L)

            minionsX += 5f
            minionsY += 10f

            // 假設畫布寬度大約是 1080，高度約是 1776（可依你實際抓到的 Canvas 尺寸調整）
            // 當小小兵跑出右下角邊界時（例如 minionsX 超過 1000 或 minionsY 超過 1700），重置回 (0, 0)
            if (minionsX > 1080f || minionsY > 1776f) {
                minionsX = 0f
                minionsY = 0f
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        Canvas(
            modifier = modifier.fillMaxSize()
        ) {
            val minionsWidth = minionsImage.width
            val minionsHeight = minionsImage.height
            // 置中公式：(畫布寬高 - 圖片寬高) / 2f
            val offsetX = (size.width - minionsWidth) / 2f
            val offsetY = (size.height - minionsHeight) / 2f

            // 將畫布起點移至置中位置後畫圖
            translate(minionsX, minionsY) {
                drawImage(minionsImage)
            }

            //繪製洋紅色的圓, 半徑 100, 圓心 (100,100)
            drawCircle(
                Color.Magenta,
                100f,
                Offset(300f, 800f)
            )

            //繪製藍色空心矩形, 左上角 (300,200), 寬度與高度 200
            drawRect(
                Color.Blue,
                Offset(300f, 200f),
                Size(200f, 200f),
                style = Stroke(width = 10f) // 使用 style 控制外框樣式
            )

            CanvasWidth = size.width
            CanvaHeight = size.height

            drawLine(
                Color.Red,
                Offset(200f, 800f),
                Offset(size.width, size.height),
                strokeWidth = 10f
            )

        }
        Column{
            Text("畫布寬度 $CanvasWidth, 長度 $CanvaHeight")
            Button(
                onClick = {gameOver = ! gameOver}
            )
            {
                if (gameOver){Text("繼續遊戲")}
                else{Text("停止遊戲")}
            }
        }

    }
}
