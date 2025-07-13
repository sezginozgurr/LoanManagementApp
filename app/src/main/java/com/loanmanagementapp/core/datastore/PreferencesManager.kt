package com.loanmanagementapp.core.datastore

/* @Singleton
class PreferencesManager @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {

    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        dataStoreManager.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getString(key: Preferences.Key<String>): Flow<String?> {
        return dataStoreManager.dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    suspend fun saveInt(key: Preferences.Key<Int>, value: Int) {
        dataStoreManager.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getInt(key: Preferences.Key<Int>): Flow<Int?> {
        return dataStoreManager.dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStoreManager.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean?> {
        return dataStoreManager.dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    suspend fun removeKey(key: Preferences.Key<*>) {
        dataStoreManager.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    suspend fun clearAll() {
        dataStoreManager.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
} */
