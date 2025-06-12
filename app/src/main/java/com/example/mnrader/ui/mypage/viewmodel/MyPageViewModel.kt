package com.example.mnrader.ui.mypage.viewmodel

import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.dataclass.Scrap
import com.example.mnrader.ui.mypage.dataclass.User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mnrader.ui.mypage.dataclass.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {

    private val _user = MutableStateFlow(User("unknown@example.com", ""))
    val user: StateFlow<User> = _user.asStateFlow()

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _scraps = MutableStateFlow<List<Scrap>>(emptyList())
    val scraps: StateFlow<List<Scrap>> = _scraps.asStateFlow()

    // dummy data 함수
    fun loadDummyUserData(email: String) {
        viewModelScope.launch {
            if (email == "123@konkuk.ac.kr") {
                val pet1 = Pet(1, "인절미", "https://example.com/dog.png", "강아지", "말티즈", "암컷", "2개월", "사람을 좋아함", null, "실종")
                val pet2 = Pet(2, "치와와", "https://example.com/dog2.png", "강아지", "치와와", "수컷", "3개월", "짖음", null, "보호중")
                val pet3 = Pet(3, "고양이", "https://example.com/cat.png", "고양이", "코숏", "암컷", "1살", "얌전함", null, "목격중")

                _pets.value = listOf(pet1, pet2, pet3)
                _posts.value = listOf(Post(1, pet1, "서울", "2025-03-24"))
                _scraps.value = listOf(
                    Scrap(1, pet2, "서울", "2024.06.07"),
                    Scrap(2, pet3, "서울", "2025.04.01")
                )
                _user.value = User(email, "서울")
            }
        }
    }

    // PetDetailScreen 저장버튼 클릭 시 실행
    fun updatePet(updatedPet: Pet) {
        val updatedList = pets.value.map { if (it.id == updatedPet.id) updatedPet else it }
        _pets.value = updatedList
    }

    // 3. 설정 화면에서 추가된 Pet을 마이페이지에 반영
    fun addPetFromSetting(pet: Pet) {
        _pets.value = _pets.value + pet
    }


    /*
    실제 서비스 구조 함수
    fun loadUserData(email: String) {
        viewModelScope.launch {
            val user = userRepository.getUser(email)         // 예: Firestore 또는 Room
            val pets = petRepository.getPetsByOwner(email)
            val posts = postRepository.getPostsByAuthor(email)
            val scraps = scrapRepository.getScrapsByUser(email)

            _user.value = user
            _pets.value = pets
            _posts.value = posts
            _scraps.value = scraps
        }
    }
    */
}
