package com.richard.pokemonsdk.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.richard.pokemonsdk.R
import com.richard.pokemonsdk.ui.adapters.SpritesAdapters
import kotlinx.android.synthetic.main.activity_search_ui.*

class SearchUi : AppCompatActivity(), SearchUiContract.SearcUi, View.OnClickListener{
    lateinit var progressDialog: ProgressDialog
    var searchPresenter : SearchPresenterImplementation? = null
    var isImageView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_ui)
        progressDialog = ProgressDialog(this)
        attachPresenter()
        setClick()
        setTitle(R.string.title)
        

    }

    override fun onRetainCustomNonConfigurationInstance() : Any?{
        return searchPresenter
    }


    override fun showProgress() {
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun hideProgress() {
        progressDialog.hide()
    }

    override fun buildView(pok: PokemonDescriptionAndSprites) {
        resultDisplay.removeAllViews()

        val  txView = TextView(this)
        val param = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        txView.layoutParams = param
        txView.textSize = 25F
        txView.setTextColor(resources.getColor(R.color.black))
        txView.setText(pok.description)
        resultDisplay.addView(txView)
    }


    override fun getContext(): Context {
        return  this.getContext();
    }


    override fun buildRecyclerView(adapter: SpritesAdapters) {
        var recyclerView : RecyclerView? = null
        resultDisplay.removeAllViews()

        recyclerView = RecyclerView(this)
        val param = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        recyclerView.layoutParams = param
        recyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.adapter = adapter
        resultDisplay.addView(recyclerView)
    }

    override fun displayError(errorMessage: String){
        resultDisplay.removeAllViews()
        val param = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val title = TextView(this)
        title.layoutParams = param
        title.textSize = 25F
        title.text = "Error"
        resultDisplay.addView(title)


        val txView = TextView(this)
        txView.layoutParams = param
        txView.textSize = 20F
        txView.text = errorMessage
        resultDisplay.addView(txView)
    }


    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnSearch -> startSearch()
            R.id.desSearchBtn -> swichSearch()
            R.id.imageSearchBtn -> swichSearch(true)

        }
    }


    private fun startSearch() {
        if(!serachView.text.toString().isNullOrEmpty()){
            val imm: InputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            if(!isImageView){
                searchPresenter!!.onDescriptionSubmit(serachView.text.toString())
            }else{
                searchPresenter!!.onImageSubmit(serachView.text.toString())
            }
        }else{
            serachView.setError(this.getString(R.string.provideValidEntry))
        }
    }


    private fun swichSearch(isImage: Boolean = false) {
        if(isImage){
            isImageView = true
            btnSearch.text = this.getText((R.string.getImage))
            imageSearchBtn.background = this.resources.getDrawable(R.drawable.button_bg_red)
            desSearchBtn.background = this.resources.getDrawable(R.drawable.button_bg_transparent)
        }else{
            isImageView = false
            btnSearch.text = this.getText(R.string.Getdescription)
            imageSearchBtn.background = this.resources.getDrawable(R.drawable.button_bg_transparent)
            desSearchBtn.background = this.resources.getDrawable(R.drawable.button_bg_red)
        }
    }


    private fun setClick(){
        btnSearch.setOnClickListener(this)
        desSearchBtn.setOnClickListener(this)
        imageSearchBtn.setOnClickListener(this)
    }

    private  fun attachPresenter(){
        if(lastNonConfigurationInstance != null){
            searchPresenter = lastNonConfigurationInstance as SearchPresenterImplementation
        }

        if(searchPresenter === null){
            searchPresenter = SearchPresenterImplementation(this)
        }
    }






}