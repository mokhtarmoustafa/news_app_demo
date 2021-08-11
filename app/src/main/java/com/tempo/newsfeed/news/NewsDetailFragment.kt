package com.tempo.newsfeed.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.tempo.newsfeed.api.NewsListModel
import com.tempo.newsfeed.databinding.FragmentNewsDetailBinding


class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentNewsDetailBinding
    private lateinit var newsModel: NewsListModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        binding.ivBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
         newsModel = args.newsItem
        binding.newsDetail = newsModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMore.setOnClickListener {

            val action = NewsDetailFragmentDirections.actionNewsDetailFragmentToWebFragment(newsModel.url!!)
            Navigation.findNavController(binding.root).navigate(action)
        }

    }
}
