/*
 * MIT License
 *
 * Copyright (c) 2024 AndroidHappyClub
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.androidhappyclub.handlersample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val handler = CustomHandler(Looper.getMainLooper()) { message ->
        if (message.what == WHAT_TAG && null != message.obj) {
            Log.d(TAG, "这是通过 Handler 的构造函数传入 handleMessage")
            Log.d(TAG, message.obj.toString())
        }
        return@CustomHandler true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // handler.obtainMessage(1, "这是一条消息。").sendToTarget()
        sendSample()
    }

    /**
     * [Handler] 的 [Handler.post] 或 [Handler.postDelayed] 示例。
     *
     * 参考 [post](https://www.yuque.com/mashangxiayu/gne1e3/wpdkg53aufu347g2#kuj3I)
     */
    private fun postSample() {
        handler.post {
            Log.d(TAG, "Handler post方法示例")
        }

        handler.postDelayed({
            Log.d(TAG, "Handler postDelayed方法示例")
        }, 1000)
    }

    /**
     * [Handler] 的 [Handler.sendMessage] [Handler.sendMessageDelayed] 或
     * [Handler.sendEmptyMessage] 示例。
     *
     * 参考 [send](https://www.yuque.com/mashangxiayu/gne1e3/wpdkg53aufu347g2#iam29)
     */
    private fun sendSample() {
        val message = Message.obtain().apply {
            what = WHAT_TAG
            obj = "这是 Handler 的 send 方法示例"
        }

        handler.sendMessage(message)
        handler.sendMessageDelayed(message, 1000)
        handler.sendEmptyMessage(WHAT_TAG)
    }

    /**
     * 参考 [handleMessage](https://www.yuque.com/mashangxiayu/gne1e3/wpdkg53aufu347g2#hwOU9)
     */
    class CustomHandler(looper: Looper, callback: Callback) : Handler(looper, callback) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "这是重写了 Handler 的 handleMessage")
        }

    }

    companion object {
        const val WHAT_TAG: Int = 0x01
        val TAG: String = MainActivity::class.java.simpleName
    }
}
