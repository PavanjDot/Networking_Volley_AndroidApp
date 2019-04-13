package pavanjdot.com.taskapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_form_signin.*
import kotlinx.android.synthetic.main.activity_form_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class FormSigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_signin)

        // Intent for Signup Layout
        login_form_btnsignup.setOnClickListener {
            var intent_for_signup = Intent(this@FormSigninActivity, FormSignupActivity::class.java)
            startActivity(intent_for_signup)
        }

        login_form_btnlogin.setOnClickListener {

//            var email = login_form_edtemail.text.toString().trim()
//            var password = login_form_edtpassword.text.toString().trim()



            try {

                val url = "http://sfs.dev.ritesh.happlabs.in/dev/sign_in "
                val queue = Volley.newRequestQueue(this@FormSigninActivity)
                val putRequest = object : StringRequest(Request.Method.PUT, url,
                    Response.Listener<String> { response ->
                        // response
                        Log.d("Response", response)
                        Toast.makeText(this@FormSigninActivity, response, Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error ->
                        // error
                        Log.d("Error.Response", error.toString() )


                        Toast.makeText(this@FormSigninActivity, error.toString(), Toast.LENGTH_SHORT).show()

                        Log.d("TAG", "Error: " + error
                                + "\nStatus Code " + error.networkResponse.statusCode
                                + "\nResponse Data " + error.networkResponse.data
                                + "\nCause " + error.cause
                                + "\nmessage" + error.message)
                    }
                )

                {

                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        //This does not appear in the log
                        Log.d("TAG", "Does it assign params?")
                        params.put("form_username", login_form_edtemail.text.toString().trim())
                        params.put("form_password", login_form_edtpassword.text.toString().trim())

                        return params
                    }

                    override fun getHeaders():Map<String, String>
                    {
                        val params = HashMap<String, String>()
                        params.put("Content-Type", "application/json")

                        return params
                    }


                }

                queue.add(putRequest)

            }catch (e: Exception){

                e.printStackTrace()
            }catch (e2: JSONException){
                e2.printStackTrace()
            }


        }
    }


}


