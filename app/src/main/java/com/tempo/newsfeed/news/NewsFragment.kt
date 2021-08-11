package com.tempo.newsfeed.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tempo.newsfeed.api.Status
import com.tempo.newsfeed.common.Default_Search
import com.tempo.newsfeed.commonUtil.ConnectionUtil
import com.tempo.newsfeed.databinding.FragmentNewsListBinding
import com.tempo.newsfeed.di.Injectable
import com.tempo.newsfeed.di.injectViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

class NewsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NewsViewModel
    private lateinit var conUtil: ConnectionUtil
    private lateinit var binding: FragmentNewsListBinding
    var adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        conUtil = ConnectionUtil(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)
        if (!conUtil.isOnline())
            Toast.makeText(
                context?.applicationContext,
                "No internet connection!",
                Toast.LENGTH_SHORT
            ).show()



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Default_Search = if (newText.toString().isEmpty())
                    "it"
                else
                    newText.toString()

                subscribeUI(adapter)
                return true
            }
        })


        binding.refresh.setOnRefreshListener {
            subscribeUI(adapter)
        }

        binding.rvNewsList.adapter = adapter

        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: NewsAdapter) {
        val data = viewModel.newsList(conUtil.isOnline())
        data?.networkState?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.FAILED -> {
                    progressBar.visibility = View.GONE
                    refresh.isRefreshing = false
                    Toast.makeText(requireContext(), "Some Thing Wrong: ${it.msg}", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    refresh.isRefreshing = false
                }
            }
        })
        data?.pagedList?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }


}
