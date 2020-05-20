package com.example.otusproject.ui.screen_invite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.otusproject.R
import kotlinx.android.synthetic.main.fragment_invite.*


class InviteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invite_btn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.inviteMessage))
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }
}
