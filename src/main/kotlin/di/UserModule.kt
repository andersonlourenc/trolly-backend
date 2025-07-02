package di

import com.lourenc.domain.user.usecase.CreateUserUseCase
import com.lourenc.domain.user.usecase.DeleteUserUseCase
import com.lourenc.domain.user.usecase.GetAllUsersUseCase
import com.lourenc.domain.user.usecase.UpdateUserUseCase
import data.user.repository.UserRepositoryImpl

object UserModule {
    private val userRepository = UserRepositoryImpl()

    val createUserUseCase = CreateUserUseCase(userRepository)
    val getAllUsersUseCase = GetAllUsersUseCase(userRepository)
    val updateUserUseCase = UpdateUserUseCase(userRepository)
    val deleteUserUseCase = DeleteUserUseCase(userRepository)
}