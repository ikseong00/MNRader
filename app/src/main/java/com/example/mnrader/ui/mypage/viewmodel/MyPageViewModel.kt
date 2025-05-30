package com.example.mnrader.ui.mypage.viewmodel

import com.example.mnrader.ui.mypage.MyPost
import com.example.mnrader.ui.mypage.Pet
import com.example.mnrader.ui.mypage.Scrap
import com.example.mnrader.ui.mypage.User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {

    private val _user = MutableStateFlow(User("unknown@example.com", ""))
    val user: StateFlow<User> = _user.asStateFlow()

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets.asStateFlow()

    private val _posts = MutableStateFlow<List<MyPost>>(emptyList())
    val posts: StateFlow<List<MyPost>> = _posts.asStateFlow()

    private val _scraps = MutableStateFlow<List<Scrap>>(emptyList())
    val scraps: StateFlow<List<Scrap>> = _scraps.asStateFlow()

    // dummy data 함수
    fun loadDummyUserData(email: String) {
        viewModelScope.launch {
            when (email) {
                "123@konkuk.ac.kr" -> {
                    _user.value = User(email, "서울 광진구")
                    _pets.value = listOf(
                        Pet("1", "흰둥이", "https://example.com/dog1.png"),
                        Pet("2", "버찌", "https://example.com/cat1.png"),
                        Pet("3", "부끄", "https://example.com/dog2.png")

                    )
                    _posts.value = listOf(
                        MyPost("1", "흰둥이", "암컷", "서울", "2025-03-24", "https://example.com/dog.png")
                    )
                    _scraps.value = listOf(
                        Scrap("scrap1", "치와와", "서울", "2024.06.07", "https://example.com/dog.png"),
                        Scrap("scrap2", "도비", "서울", "2025.04.01", "https://example.com/cat.png")
                    )
                }

                "blue@konkuk.ac.kr" -> {
                    _user.value = User(email, "서울 ")
                    _pets.value = listOf(
                        Pet("1", "말티즈", "https://example.com/maltese.png")
                    )
                    _posts.value = listOf(
                        MyPost("2", "초코", "수컷", "서울 ", "2025-02-11", "https://example.com/choco.png")
                    )
                    _scraps.value = listOf(
                        Scrap("scrap3", "코숏", "서울 ", "2025.01.17", "https://example.com/kitty.png")
                    )
                }

                else -> {
                    _user.value = User(email, "서울")
                    _pets.value = emptyList()
                    _posts.value = emptyList()
                    _scraps.value = emptyList()
                }
            }
        }
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
