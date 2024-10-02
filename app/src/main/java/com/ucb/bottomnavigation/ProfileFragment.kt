package com.ucb.bottomnavigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var subscribeCheckBox: CheckBox
    private lateinit var notSubscribeCheckBox: CheckBox
    private lateinit var saveButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nameEditText = view.findViewById(R.id.nameEditText)
        ageEditText = view.findViewById(R.id.ageEditText)
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup)
        subscribeCheckBox = view.findViewById(R.id.subscribeCheckBox)
        notSubscribeCheckBox = view.findViewById(R.id.notSubscribeCheckBox)
        saveButton = view.findViewById(R.id.saveButton)

        // Load saved data if available
        loadProfile()

        saveButton.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val genderId = genderRadioGroup.checkedRadioButtonId
        val isSubscribed = subscribeCheckBox.isChecked
        val isNotSubscribed = notSubscribeCheckBox.isChecked

        // Check for empty fields
        if (name.isEmpty() || age.isEmpty() || genderId == -1) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = when (genderId) {
            R.id.genderMale -> "Male"
            R.id.genderFemale -> "Female"
            else -> "Not Specified"
        }

        // Save data using SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("name", name)
            putString("age", age)
            putString("gender", gender)
            putBoolean("isSubscribed", isSubscribed)
            putBoolean("isNotSubscribed", isNotSubscribed)
            apply()  // or commit() for synchronous saving
        }

        Toast.makeText(requireContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfile() {
        val sharedPreferences = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        nameEditText.setText(sharedPreferences.getString("name", ""))
        ageEditText.setText(sharedPreferences.getString("age", ""))

        // Load gender
        val gender = sharedPreferences.getString("gender", "")
        when (gender) {
            "Male" -> genderRadioGroup.check(R.id.genderMale)
            "Female" -> genderRadioGroup.check(R.id.genderFemale)
        }

        // Load checkboxes
        subscribeCheckBox.isChecked = sharedPreferences.getBoolean("isSubscribed", false)
        notSubscribeCheckBox.isChecked = sharedPreferences.getBoolean("isNotSubscribed", false)
    }
}
