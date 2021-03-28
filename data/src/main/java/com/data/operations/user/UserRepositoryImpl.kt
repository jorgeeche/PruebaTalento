package com.data.operations.user;

import com.data.operations.login.*
import com.data.operations.remote.ParsedResponse
import com.data.operations.remote.RemoteDataSourceExecutor
import com.domain.operations.login.*
import com.domain.operations.user.UserRepository
import commons.android.Either

class UserRepositoryImpl(
        private val userInMemoryDataSource: UserInMemoryDataSource,
        private val loginRemoteDataSource: LoginRemoteDataSource,
        private val remoteDataSourceExecutor: RemoteDataSourceExecutor,
        private val userPreferencesDataSource: UserPreferencesDataSource

    ) : UserRepository {

    override suspend fun callUserLogin(params: LoginCredentials) : Either<CallLoginFailure, LoginDomainResponse> {
        val parsedResponse: ParsedResponse<LoginError, LoginResponse> = remoteDataSourceExecutor { loginRemoteDataSource.callLogin(params) }

        return when (parsedResponse) {
            is ParsedResponse.Success ->{
                userPreferencesDataSource.saveAlias(params.email)
                userPreferencesDataSource.saveName(parsedResponse.success.data.user.name)
                userPreferencesDataSource.saveRole(parsedResponse.success.data.user.role)
                userPreferencesDataSource.updateUserLoggedStatus(true)

                Either.Right(parsedResponse.success.data.toDomain())
            }
            is ParsedResponse.KnownError -> Either.Left(
                    CallLoginFailure.Known(
                            parsedResponse.knownError
                    )
            )
            is ParsedResponse.Failure -> Either.Left(CallLoginFailure.Repository(parsedResponse.failure))
        }
    }

    override suspend fun clearAppData(): Either<Unit, Unit> {
        TODO("Not yet implemented")
        userPreferencesDataSource.updateUserLoggedStatus(false)

    }

    override suspend fun clearUserData(): Either<Unit, Unit> {
        TODO("Not yet implemented")
    }


    private fun UserLogin.toDomain() = UserDomainLogin(
            email = email,
            name = name,
            is_first_login = is_first_login,
            email_confirmed = email_confirmed,
            profile_image_url = profile_image_url,
            role = role

    )
    private fun LoginDataResponse.toDomain() = LoginDomainResponse(
            token = token,
            user = user.toDomain()
    )
}





        /*private val loginRemoteDataSource: LoginRemoteDataSource,)
        private val remoteDataSourceExecutor: RemoteDataSourceExecutor,
        cardInMemoryDataSource: CardDetailInMemoryDataSource,
        resourcesInMemoryDataSource: ResourcesInMemoryDataSource,
        private val userInMemoryDataSource: UserInMemoryDataSource,
        private val userPreferencesDataSource: UserPreferencesDataSource,
        private val wizinkInMemoryDataSource: WizinkInMemoryDataSource,*/

/*
private val inMemoryDataSources: List<InMemoryDataSource> = listOf(cardInMemoryDataSource, resourcesInMemoryDataSource, userInMemoryDataSource, wizinkInMemoryDataSource)

        override suspend fun checkUserLogin(loginCredentials: LoginCredentials): Either<CheckLoginFailure, LoginToken> {
        val loginParams = LoginParams(loginCredentials.alias, loginCredentials.password)

        val parsedResponse: ParsedResponse<LoginError, LoginResponse> = remoteDataSourceExecutor { loginRemoteDataSource.checkLogin(loginParams) }

        when (parsedResponse) {
        is ParsedResponse.Success -> {
        userPreferencesDataSource.saveAlias(loginCredentials.alias)
        userPreferencesDataSource.savePwd(loginCredentials.password)
        userPreferencesDataSource.updateUserLoggedStatus(true)
        wizinkInMemoryDataSource.saveToken(parsedResponse.success.userToken)
        wizinkInMemoryDataSource.saveCmfId(parsedResponse.success.customerMasterFileId?:"")
        userPreferencesDataSource.saveShortName(parsedResponse.success.name)
        DeviceLinkingHelper.lastLoginSaved = parsedResponse.success.lastLoginSaved?:""
        DeviceLinkingHelper.nameUser = parsedResponse.success.name
        return Right(parsedResponse.success.toDomain())
        }
        is ParsedResponse.KnownError -> {
        wizinkInMemoryDataSource.removeKeySecret()
        return Left(CheckLoginFailure.Known(parsedResponse.knownError))
        }
        is ParsedResponse.Failure -> {
        wizinkInMemoryDataSource.removeKeySecret()
        return Left(CheckLoginFailure.Repository(parsedResponse.failure))
        }
        }
        }

private fun LoginResponse.toDomain() = LoginToken(userToken)


        override suspend fun clearAppData(): Either<Unit, Unit> {
        userPreferencesDataSource.updateUserLoggedStatus(false)
        remoteDataSourceExecutor { loginRemoteDataSource.logout() }
        removeInMemoryData()
        return Right(Unit)
        }

private fun removeInMemoryData() {
        inMemoryDataSources.forEach { it.clearData() }
        }


        override suspend fun clearUserData(): Either<Unit, Unit> {
        fingerprintManager.deleteToken()
        userInMemoryDataSource.updateIsFirstLogin(true)
        userPreferencesDataSource.clearData()
        removeInMemoryData()
        return Right(Unit)
        }


        override suspend fun getAccessRestrictions(): Either<GetAccessRestrictionsFailure, List<AccessRestriction>> {
        val parsedResponse: ParsedResponse<Unit, Array<AccessRestrictionResponse>> = remoteDataSourceExecutor {
        loginRemoteDataSource.getAccessRestrictions()
        }

        when (parsedResponse) {
        is ParsedResponse.Success -> {
        var success: Array<AccessRestrictionResponse> = parsedResponse.success
        val accessRestriction = mutableListOf<AccessRestriction>()

        success.forEach {
        when (it.type) {
        "MULTICHANNEL_CONTRACT" -> accessRestriction.add(AccessRestriction.MultichannelContract)
        "ONBOARDING" -> accessRestriction.add(AccessRestriction.OnBoarding(it.cardIds ?: listOf()))
        "DEVICE_LINKING" -> accessRestriction.add(AccessRestriction.DeviceLinking(it.accessType ?: ""))
        }
        }

        return Right(accessRestriction.toList())
        }
        is ParsedResponse.KnownError -> {
        wizinkInMemoryDataSource.saveKeySecret(null)
        return Left(GetAccessRestrictionsFailure.Repository(RepositoryFailure.Unknown))
        }
        is ParsedResponse.Failure -> {
        wizinkInMemoryDataSource.saveKeySecret(null)
        return Left(GetAccessRestrictionsFailure.Repository(parsedResponse.failure))
        }
        }
        }

        }*/

