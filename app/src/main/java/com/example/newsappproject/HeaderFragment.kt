package com.example.newsappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.newsappproject.databinding.FragmentHeaderBinding
import com.google.firebase.auth.FirebaseAuth

class HeaderFragment : Fragment() {
    private lateinit var binding: FragmentHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeaderBinding.inflate(inflater, container, false)
        binding.starIcon.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_favouritesFragment)
        }
        binding.menuIcon.setOnClickListener { view ->
            val popup = PopupMenu(requireContext(), view)
            popup.menuInflater.inflate(R.menu.header_menu, popup.menu)

            try {
                val fields = popup.javaClass.getDeclaredField("mPopup")
                fields.isAccessible = true
                val menuPopupHelper = fields.get(popup)
                val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                val setForceIcons = classPopupHelper.getMethod("setForceShowIcon", Boolean::class.java)
                setForceIcons.invoke(menuPopupHelper, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_settings -> {
                        findNavController().navigate(R.id.action_HomeFragment_to_settingsFragment)
                        true
                    }
                    R.id.menu_logout -> {
                        FirebaseAuth.getInstance().signOut()
                        findNavController().navigate(R.id.action_HomeFragment_to_LoginFragment)
                        true
                    }
                    else -> false
                }
            }



            popup.show()
        }
        return binding.root
    }
}