package com.kiwicorp.supersimplegymapp.di

import android.content.Context
import androidx.room.Room
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import com.kiwicorp.supersimplegymapp.data.source.RoutineRepository
import com.kiwicorp.supersimplegymapp.data.source.WorkoutRepository
import com.kiwicorp.supersimplegymapp.data.source.local.SuperSimpleGymAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideActivityRepository(superSimpleGymAppDatabase: SuperSimpleGymAppDatabase): ActivityRepository {
        return ActivityRepository(superSimpleGymAppDatabase.activityDao())
    }

    @Singleton
    @Provides
    fun provideWorkoutRepository(superSimpleGymAppDatabase: SuperSimpleGymAppDatabase): WorkoutRepository {
        return with(superSimpleGymAppDatabase) { WorkoutRepository(workoutDao(), workoutEntryDao()) }
    }

    @Singleton
    @Provides
    fun provideRoutineRepository(superSimpleGymAppDatabase: SuperSimpleGymAppDatabase): RoutineRepository {
        return with(superSimpleGymAppDatabase) { RoutineRepository(routineDao(), routineEntryDao()) }
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SuperSimpleGymAppDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            SuperSimpleGymAppDatabase::class.java,
            "Main.db"
        ).build()
    }
}