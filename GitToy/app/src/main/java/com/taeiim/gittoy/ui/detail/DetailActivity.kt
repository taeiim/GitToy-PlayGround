package com.taeiim.gittoy.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.taeiim.gittoy.R
import com.taeiim.gittoy.base.BaseActivity
import com.taeiim.gittoy.databinding.ActivityDetailBinding
import com.taeiim.gittoy.ui.RepoRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    val vm by viewModels<DetailViewModel>()

    private val userName by lazy { intent?.getStringExtra(RepoRecyclerAdapter.KEY_USER_NAME) ?: "" }
    private val repoName by lazy { intent?.getStringExtra(RepoRecyclerAdapter.KEY_REPO_NAME) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        vm.setObserves()
        loadDetailData()
    }

    private fun DetailViewModel.setObserves() {
        repo.observe(this@DetailActivity, Observer {
            binding.repo = it
            if (intent.getBooleanExtra(KEY_IS_SAVE_CLICK_HISTORY, false))
                vm.saveRepoHistory()
        })
        user.observe(this@DetailActivity, Observer { binding.user = it })
    }

    private fun loadDetailData() {
        vm.loadRepoData(userName, repoName)
        vm.loadUserData(userName)
    }

    companion object {
        const val KEY_IS_SAVE_CLICK_HISTORY = "isSaveClickHistory"
    }

}