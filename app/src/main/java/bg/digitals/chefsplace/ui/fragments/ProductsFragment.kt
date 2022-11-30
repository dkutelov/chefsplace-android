package bg.digitals.chefsplace.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bg.digitals.chefsplace.ProductsViewModel
import bg.digitals.chefsplace.R
import bg.digitals.chefsplace.adapters.ProductsAdapter
import bg.digitals.chefsplace.databinding.FragmentProductsBinding
import bg.digitals.chefsplace.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding

    private lateinit var productViewModel: ProductsViewModel

    private val mAdapter by lazy { ProductsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductsBinding.inflate(LayoutInflater.from(context))

        productViewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]

        setupRecycleView()
        requestApiData()

        return binding.root
    }

    private fun requestApiData() {
        productViewModel.getProducts("")

        productViewModel.productsResponse.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    //show shimmer
                    val a = 1
                }
            }


        }
    }

    private fun setupRecycleView() {
        binding.productList.adapter = mAdapter
        binding.productList.layoutManager = LinearLayoutManager(requireContext())
    }
}