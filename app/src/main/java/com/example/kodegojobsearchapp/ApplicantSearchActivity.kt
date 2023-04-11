package com.example.kodegojobsearchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodegojobsearchapp.adapter.ApplicantAdapter
import com.example.kodegojobsearchapp.databinding.ActivityApplicantSearchBinding
import com.example.kodegojobsearchapp.employer_viewpager.EmployerActivity
import com.example.kodegojobsearchapp.model.JobSeeker

// TODO: (anyone) applicant search implementation
class ApplicantSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplicantSearchBinding

    private lateinit var applicantAdapter: ApplicantAdapter
    private var applicants: ArrayList<JobSeeker> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicantSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        init()

        applicantAdapter = ApplicantAdapter(applicants)
        binding.applicantList.layoutManager = LinearLayoutManager(applicationContext)
        binding.applicantList.adapter = applicantAdapter

        binding.returnHome.setOnClickListener {
            val intent = Intent(this, EmployerActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun init(){
        applicants = arrayListOf(
            JobSeeker(
                "Jane Smith",
                "UX Designer",
                4.0,
                "Skilled UX designer with experience in wireframing, prototyping, and user testing."
            ),
            JobSeeker(
                "Michael Johnson",
                "Data Analyst",
                4.2,
                "Data analyst with proficiency in SQL, Excel, and Tableau for data visualization."
            ),
            JobSeeker(
                "Sarah Lee",
                "Product Manager",
                4.7,
                "Proven track record in managing product development lifecycles and leading cross-functional teams."
            ),
            JobSeeker(
                "David Kim",
                "Graphic Designer",
                3.8,
                "Creative graphic designer with experience in branding, typography, and print design."
            ),
            JobSeeker(
                "Karen Park",
                "Marketing Specialist",
                4.3,
                "Results-driven marketing specialist with expertise in SEO, SEM, and email marketing."
            ),
            JobSeeker(
                "Robert Chen",
                "Frontend Developer",
                4.1,
                "Frontend developer with strong skills in HTML, CSS, and JavaScript frameworks such as React and Angular."
            ),
            JobSeeker(
                "Grace Wang",
                "Product Designer",
                4.4,
                "Product designer with a strong portfolio of work in designing mobile and web applications."
            ),
            JobSeeker(
                "James Lee",
                "Data Scientist",
                4.6,
                "Data scientist with experience in statistical modeling, machine learning, and data visualization."
            ),
            JobSeeker(
                "Emily Kim",
                "Social Media Manager",
                4.2,
                "Expert in managing social media campaigns and increasing brand awareness on platforms such as Facebook, Instagram, and Twitter."
            )
        )
    }

}