package com.example.moviescope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviescope.databinding.ActivitySlideShowImageContainerBinding

class SlideShowAdapter(
    private val imageList: ArrayList<Int>,
    private val viewPager: ViewPager2
): RecyclerView.Adapter<SlideShowAdapter.SlideShowViewHolder>() {
    class SlideShowViewHolder(
        private val binding: ActivitySlideShowImageContainerBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.iv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideShowViewHolder {
        return SlideShowViewHolder(ActivitySlideShowImageContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: SlideShowViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if (position == imageList.size - 1) {
            viewPager.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}