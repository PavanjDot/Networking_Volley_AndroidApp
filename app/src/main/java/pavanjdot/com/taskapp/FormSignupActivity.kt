package pavanjdot.com.taskapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_form_signup.*
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.HashMap

class FormSignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_signup)

        // Navigates back to Login Activity.
        signup_form_btnlogin.setOnClickListener {

            finish()
        }

        signup_form_btnsignup.setOnClickListener {

            if (signup_form_edtpassword.text.toString().equals(signup_form_edtconfirmpassword.text.toString())) {

                var email = signup_form_edtemail.text.toString().trim()
                var uname = signup_form_edtusername.text.toString().trim()
                var password = signup_form_edtpassword.text.toString().trim()
                var confirm_password = signup_form_edtconfirmpassword.text.toString().trim()

                try {

                    val url = "http://sfs.dev.ritesh.happlabs.in/dev/sign_up/"
                    val JsonObj = JSONObject()

                    JsonObj.put("form_email", email)
                    JsonObj.put("form_uname", uname)
                    JsonObj.put("form_password", password)
                    JsonObj.put("form_confirm_password", confirm_password)

                    val queue = Volley.newRequestQueue(this@FormSignupActivity)
                    val putRequest = object : JsonObjectRequest(Request.Method.PUT, url, JsonObj,
                        Response.Listener<JSONObject> { response ->
                            // response
                            Log.d("Response", response.toString())
                            Toast.makeText(this@FormSignupActivity, response.toString(), Toast.LENGTH_SHORT).show()
                        },
                        Response.ErrorListener { error ->
                            // error
                            Log.d("Error.Response", error.toString() )

                            Toast.makeText(this@FormSignupActivity, error.toString(), Toast.LENGTH_SHORT).show()
                        }
                    ) {




                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            //This does not appear in the log
                            Log.d("TAG", "Does it assign params?")
                            //params.put("Content-Type", "application/json")
//                            params.put("form_email", email)
//                            params.put("form_uname", uname)
//                            params.put("form_password", password)
//                            params.put("form_confirm_password", confirm_password)

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




}
