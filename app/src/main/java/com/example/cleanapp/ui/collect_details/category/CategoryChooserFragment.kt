package com.example.cleanapp.ui.collect_details.category

import android.graphics.Bitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.CategoryChooserFragmentBinding
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryChooserFragment :
    BaseFragment<CategoryChooserFragmentBinding>(CategoryChooserFragmentBinding::inflate) {

    private val viewModel: CategoryChooserViewModel by viewModels()
    private val shareViewModel: ChooserViewModel by activityViewModels()
    private lateinit var adapter: CategoryAdapter

    private lateinit var bitmap: Bitmap

    override fun start() {
        initRecycler()
        observes()
        setListeners()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
//            saveAsPdf(binding.root)
            requireActivity().findNavController(R.id.nav_host_fragment).navigateUp()
        }
    }

    private fun initRecycler() {
        adapter = CategoryAdapter().apply {
            chooseCategory = {

                val order = Order()
                order.category = it

                shareViewModel.setFragmentTitle(it.categoryEn)


                findNavController().navigate(
                    R.id.action_categoryChooserFragment_to_chooserDateFragment,
                    bundleOf("order" to order)
                )

            }
        }
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCategory()

    }

    private fun observes() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> adapter.setItems(it.data!!)

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> {}
            }
        })
    }

//    private fun saveAsPdf(layout: ConstraintLayout) {
//        bitmap = loadBitmap(layout, layout.width, layout.height)
//        createPdf()
//    }
//
//    private fun loadBitmap(layout: ConstraintLayout, width: Int, height: Int): Bitmap {
//        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        layout.draw(canvas)
//        return bitmap
//    }
//
//    private fun createPdf() {
//        val displayMetrics = DisplayMetrics()
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            requireActivity().display?.getRealMetrics(displayMetrics)
//        } else {
//            requireActivity().windowManager.defaultDisplay.getRealMetrics(displayMetrics)
//        }
//
//        val convertWidth = displayMetrics.widthPixels
//        val convertHeight = displayMetrics.heightPixels
//
//        val pdfDocument = PdfDocument()
//        val pageInfo = PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create()
//        val page = pdfDocument.startPage(pageInfo)
//        val canvas = page.canvas
//        val paint = Paint()
//        canvas.drawPaint(paint)
//        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true)
//        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
//        pdfDocument.finishPage(page)
//
//        val file = File(getFilePath())
//
//        if (file.exists()) {
//            d("PDF", "FILE EXISTS")
//            openPdf(file.path)
//        } else {
//            d("PDF", "FILE Doesnt EXISTS")
//        }
//        try {
//            val out = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
//            out.flush()
//            out.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun openPdf(path: String) {
//        val file = File(path)
//        if (file.exists()) {
//            val intent = Intent(Intent.ACTION_VIEW)
//            val uri = Uri.fromFile(file)
//            intent.setDataAndType(uri, "application/pdf")
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//
//            try {
//                startActivity(intent)
//            } catch (e: ActivityNotFoundException) {
//                Toast.makeText(requireContext(), "Cannot open pdf file", Toast.LENGTH_SHORT).show()
//                d("PDF", "ERROR OPEN")
//            }
//        }
//    }
//
//    private fun getFilePath() : String {
//        val contextWrapper = ContextWrapper(context)
//        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
//        val file = File(documentDirectory, "reservation.pdf")
//        return file.path
//    }
}