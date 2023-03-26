package uk.ac.aber.cs31620.learningapp.datasource.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.cs31620.learningapp.datasource.AppRepository
import uk.ac.aber.cs31620.learningapp.datasource.entity.Translation
import uk.ac.aber.cs31620.learningapp.datasource.entity.translationHelper.TranslationSelectQuestion

class TranslationViewModel(application: Application) : AndroidViewModel(application){
    private val repository: AppRepository = AppRepository(application)
    private val dbCoroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    var translationsList: LiveData<List<Translation>> = repository.getAllTranslations()
        private set

    var singleTranslationSearch: LiveData<Translation> = MutableLiveData(Translation("", ""))
        private set

    var singleRandomTranslation: LiveData<Translation> = repository.getRandomTranslation()
        private set

    var randomTranslationId: LiveData<Long> = repository.getRandomTranslationId()

    var selectQuestion: LiveData<TranslationSelectQuestion> = repository.getSelectableQuizQuestion(null)
        private set

    var numberOfTranslations: LiveData<Int> = repository.getTranslationsCount()

//    fun findNewRandomTranslation(){
//        singleRandomTranslation = repository.getRandomTranslation()
//    }

    fun findTranslationById(translationId: Long){
        singleTranslationSearch = repository.getTranslationById(translationId)
    }

    fun addNewTranslation(data: Translation, afterExecution: () -> Unit = {}){
        dbCoroutineScope.launch {
            repository.insert(data)
            afterExecution()
        }
    }

    fun updateTranslationById(data: Translation){
        dbCoroutineScope.launch {
            repository.updateTranslation(data)
        }
    }

    fun getSelectedQuizQuestion(id: Long){
        selectQuestion = repository.getSelectableQuizQuestion(id)
    }

    fun deleteTranslationById(id: Long, afterExecution: () -> Unit = {}){
        dbCoroutineScope.launch {
            repository.deleteTranslation(id)
            afterExecution()
        }
    }

    fun deleteAllTranslations(afterExecution: () -> Unit = {}){
        dbCoroutineScope.launch {
            repository.deleteAllTranslations()
            afterExecution()
        }
    }
}