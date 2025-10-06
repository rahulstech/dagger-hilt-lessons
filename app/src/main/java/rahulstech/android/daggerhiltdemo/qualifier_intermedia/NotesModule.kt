package rahulstech.android.daggerhiltdemo.qualifier_intermedia

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rahulstech.android.daggerhiltdemo.qualifier_intermedia.datasource.NoteLocalDataSource
import rahulstech.android.daggerhiltdemo.qualifier_intermedia.datasource.NoteRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesModule {

    @Binds
    @LocalSource
    abstract fun getNoteLocalDataSource(impl: NoteLocalDataSource): NoteSource

    @Binds
    @RemoteSource
    abstract fun getNoteRemoteDataSource(impl: NoteRemoteDataSource): NoteSource
}