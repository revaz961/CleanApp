package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.DisplayMetrics
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.viewModels
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.OrderDetailsFragmentBinding
import com.example.cleanapp.extensions.*
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.OrderStatusEnum
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
        initView()
        observes()
    }

    private fun initView() {

        with(binding) {
            when (order.status) {
                OrderStatusEnum.ONGOING.status -> {
                    tvOrderStatus.setTextById(R.string.ongoing)
                    tvOrderStatus.setTextColor(Color.GREEN)
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
            tvCatPrice.setResourceHtmlText(R.string.cat_price, order.categoryId, order.price)
            tvAddress.text = order.address

            imgAuthor.load(master.user?.imgUrl)
            tvRating.text = master.rating.toString()

            tvReservationDateValue.text = order.reservationDate?.toDateFormat("MMM DD") ?: "error"
            tvCleaningDayValue.text = order.date?.toDateFormat("MMM DD") ?: "error"
            tvCleaningTimeValue.text = order.date?.toDateFormat("h:mm a") ?: "error"

            val cleaningFee = order.price * order.duration!!
            val serviceFee = cleaningFee * 0.2
            val totalFee = cleaningFee + serviceFee
            tvCategoryFee.setResourceHtmlText(R.string.cleaning_fee, order.price, order.duration)
            tvServiceFeeValue.text = cleaningFee.toString()
            tvServiceFeeValue.text = serviceFee.toString()
            tvTotalValue.text = totalFee.toString()

            if (order.status == OrderStatusEnum.ONGOING.status) {
                tvCancelReservation.setOnClickListener {
                    cancelReservation(order)
                }
            } else {
                tvCancelReservation.gone()
            }
            btnSavePdf.setOnClickListener {
                saveAsPdf(binding.root)
            }
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
                    }
                }
            })
        }
    }

    private fun saveAsPdf(layout: LinearLayout) {
        bitmap = loadBitmap(layout, layout.width, layout.height)
        createPdf()
    }

    private fun loadBitmap(layout: LinearLayout, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        layout.draw(canvas)
        return bitmap
    }

    private fun createPdf() {
        val displayMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().display?.getRealMetrics(displayMetrics)
        } else {
            requireActivity().windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        }

        val convertWidth = displayMetrics.widthPixels
        val convertHeight = displayMetrics.heightPixels

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()
        canvas.drawPaint(paint)
        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true)
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
        pdfDocument.finishPage(page)

        //target pdf download
        val targetPdf = "${Environment.getExternalStorageDirectory().path}/reservation.pdf"
        val file = File(targetPdf)
        try {
            pdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
            makeText(requireContext(), "Something went wrong, try again", Toast.LENGTH_SHORT).show()

            //close the document
            pdfDocument.close()
            makeText(requireContext(), "Pdf downloaded", Toast.LENGTH_SHORT).show()

            openPdf()
        }
    }

    private fun openPdf() {
        val file = File("${Environment.getExternalStorageDirectory().path}/reservation.pdf")
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.fromFile(file)
            intent.setDataAndType(uri, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                makeText(requireContext(), "Cannot open pdf file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}