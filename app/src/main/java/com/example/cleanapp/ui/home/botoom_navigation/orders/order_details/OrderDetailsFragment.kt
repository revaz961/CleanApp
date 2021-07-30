package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import android.graphics.Bitmap
import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.OrderDetailsFragmentBinding
import com.example.cleanapp.extensions.*
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.OrderStatusEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment :
    BaseFragment<OrderDetailsFragmentBinding>(OrderDetailsFragmentBinding::inflate) {

    private val viewModel: OrderDetailsViewModel by viewModels()
    private lateinit var order: Order
    private lateinit var master: Master
    private lateinit var bitmap: Bitmap

    override fun start() {
        order = arguments?.getParcelable("order")!!
        order.masterUid?.let { viewModel.getMaster(it) }
        observes()
        setListeners()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_homeFragment)
        }
    }

    private fun initView() {

        with(binding) {
            when (order.status) {
                OrderStatusEnum.ONGOING.status -> {
                    tvOrderStatus.setTextById(R.string.ongoing)
                    tvOrderStatus.setTextColor(resources.getColor(R.color.text_green))
                }
                OrderStatusEnum.FINISHED.status -> {
                    tvOrderStatus.setTextById(R.string.finished)
                    tvOrderStatus.setTextColor(Color.GRAY)
                }
                OrderStatusEnum.CANCELLED.status -> {
                    tvOrderStatus.setTextById(R.string.cancelled)
                    tvOrderStatus.setTextColor(Color.RED)
                }
            }

            master.user?.let {
                tvFullname.setTextById(
                    R.string.master_full_name,
                    it.name,
                    master.user!!.surname
                )
            }
            tvCatPrice.setResourceHtmlText(
                R.string.cat_price,
                order.category?.categoryEn,
                order.price
            )
            tvAddress.text = order.address

            imgAuthor.loadFromStorage(master.user?.imgUrl!!)
            tvRating.text = master.rating?.toString() ?: "0.0"

            tvReservationDateValue.text = order.reservationDate?.toDateFormat("MMM dd") ?: "error"
            tvCleaningDayValue.text = order.date?.toDateFormat("MMM dd") ?: "error"
            tvCleaningTimeValue.text = order.date?.toDateFormat("h:mm a") ?: "error"

            val cleaningFee = order.price * order.duration!!.minuteToHoursFloat()
            val serviceFee = cleaningFee * 0.2f
            val totalFee = cleaningFee + serviceFee
            tvCategoryFee.setResourceHtmlText(
                R.string.cleaning_fee,
                order.price,
                order.duration!!.minuteToHoursFloat()
            )
//
//            tvServiceFeeValue.text = cleaningFee.roundToDecimal().toString()
//            tvCategoryFeeValue.text = serviceFee.roundToDecimal().toString()
//            tvTotalValue.text = totalFee.roundToDecimal().toString()
            tvServiceFeeValue.text = order.getServiceFee().roundToDecimal().toString()
            tvCategoryFeeValue.text = order.getCleaningPrice().roundToDecimal().toString()
            tvTotalValue.text = order.getTotalPrice().roundToDecimal().toString()

            if (order.status == OrderStatusEnum.ONGOING.status) {
                tvCancelReservation.setOnClickListener {
                    cancelReservation(order)
                }
            } else {
                tvCancelReservation.gone()
            }
//            btnSavePdf.setOnClickListener {
//                saveAsPdf(binding.root)
//            }
        }
    }

    private fun cancelReservation(order: Order) {
        viewModel.cancelReservation(order)
    }

    private fun observes() {

        order.masterUid?.let {
            viewModel.ordersDetailsLiveData.observe(viewLifecycleOwner, {
                when (it) {
                    is ResultHandler.Success -> {
                        master = it.data!!
                        initView()
                        binding.progress.gone()
                    }

                    is ResultHandler.Error -> {
                        showErrorDialog(it.message)
                        binding.progress.gone()
                    }

                    is ResultHandler.Loading -> {
                        binding.progress.goneIf(!it.loading)
                    }
                }
            })

            viewModel.orderStatusLiveData.observe(viewLifecycleOwner, {
                when (it) {
                    is ResultHandler.Success -> {
                        order.status = it.data!!
                        initView()
                        binding.progress.gone()
                    }

                    is ResultHandler.Error -> {
                        showErrorDialog(it.message)
                        binding.progress.gone()
                    }

                    is ResultHandler.Loading -> {
                        binding.progress.goneIf(!it.loading)
                    }
                }
            })
        }
    }

//    private fun saveAsPdf(layout: LinearLayout) {
//        bitmap = loadBitmap(layout, layout.width, layout.height)
//        createPdf()
//    }
//
//    private fun loadBitmap(layout: LinearLayout, width: Int, height: Int): Bitmap {
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
//            Log.d("PDF", "FILE EXISTS")
//            openPdf(file.path)
//        } else {
//            Log.d("PDF", "FILE Doesnt EXIST")
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
//                makeText(requireContext(), "Cannot open pdf file", Toast.LENGTH_SHORT).show()
//                Log.d("PDF", "ERROR OPEN")
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