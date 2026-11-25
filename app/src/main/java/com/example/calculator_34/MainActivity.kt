package com.example.calculator_34

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display:TextView //แสดงผลลัพธ์ตัวแรก
    private var current = "" //คำที่กำลังพิมพ์อยู่
    private var operator = ""  //เครื่องหมายที่เลือก เช่น +,-,*,/
    private var firstNumber = 0.0  //เลขตัวแรก
    private var secondNumber = 0.0  //เลขตัวที่ 2
    private var result = 0.0    //เก็บผลลัพธ์ในการคำนวณ
    private var isNewOperation = true //ใช้รีเซ็ทหน้าจอ เริ่มคำนวณใหม่

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อม textview เข้ากับ layout
        display = findViewById(R.id.txtDisplay)

        //กลุ่มปุุ่มตัวเลข 0-9
        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
            )
        //กำหนด event ให้ปุ่มตัวเลขทุกปุ่ม
        for (id in numberButton){
            findViewById<Button>(id).setOnClickListener {
                numberClick((it as Button).text.toString())
            }
        }
        //เชื่อมโยงปุ่มคำนวณ + - * /
        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { operatorClicked("/") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnMod).setOnClickListener { operatorClicked("%") }

//        //เชื่อมโยงปุ่มพิเศษ = AC . +-
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnAC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toogleSign() }
        findViewById<Button>(R.id.btnDel).setOnClickListener { backSpace() }

    }
    //เมื่อกดปุ่มตัวเลข
    private fun numberClick(value: String){
        if(isNewOperation){
            display.text = ""
            isNewOperation = false
        }
        current += value
        display.text = current
    }

    private fun operatorClicked(op: String){
        if (current.isNotEmpty()){
            firstNumber = current.toDouble()
            operator = op
            current = ""
            isNewOperation = true
        }
    }

    private fun calculateResult(){
        if (current.isNotEmpty()){
            secondNumber = current.toDouble()
            result = when (operator){
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                "%" -> if (secondNumber != 0.0) firstNumber % secondNumber else Double.NaN
                else -> 0.0
            }
            //ถ้าหารด้วย 0
            display.text = if (result.isNaN()) "Cannot divide by zero" else result.toString()
            current = result.toString()
            isNewOperation = true
        }

    }
    //ฟังก์ชั่นของปุ่ม AC (all clear)
    private fun clearAll(){
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        isNewOperation = true
    }

    //ฟังก์ชั่นเพิ่มจุดทศนิยม (Dot)
    private fun addDot()
    {
        if (!current.contains('.'))
        {
            if (current.isEmpty()) current = "0." //ถ้ายังไม่มีเลข ให้เริ่มต้นด้วย 0
            else current += "."
            display.text = current
        }
    }
    //ฟังก์ชั่นสลับเครื่องหมายบวกลบ (Sign)
    private fun toogleSign()
    {
        if (current.isNotEmpty() && current != "0")
        {
            current = if (current.startsWith("-")) current.substring(1) else "-$current"
            display.text = current
        }
    }
    //ฟังก์ชั่นลบตัวล่าสุด
    private fun backSpace()
    {
        if (isNewOperation) return //ถ้าจอกำลังแสดงผลลัพธ์ให้ไม่ทำอะไร
        if (current.isNotEmpty())
        {
            current = current.dropLast(1)//ตัดตัวสุดท้ายออก 1 ตัว
            if (current == "-" ||current.isEmpty())
            {
                current = ""
                display.text = "0"
            }
            else
            {
                display.text = current
            }
        }
    }


}