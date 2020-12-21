package com.kiwicorp.dumbstrength.di

import android.content.Context
import androidx.room.Room
import com.kiwicorp.dumbstrength.data.source.ActivityRepository
import com.kiwicorp.dumbstrength.data.source.RoutineRepository
import com.kiwicorp.dumbstrength.data.source.WorkoutRepository
import com.kiwicorp.dumbstrength.data.source.local.DumbStrengthDatabase
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
    fun provideActivityRepository(dumbStrengthDatabase: DumbStrengthDatabase): ActivityRepository {
        return ActivityRepository(dumbStrengthDatabase.activityDao())
    }

    @Singleton
    @Provides
    fun provideWorkoutRepository(dumbStrengthDatabase: DumbStrengthDatabase): WorkoutRepository {
        return with(dumbStrengthDatabase) { WorkoutRepository(workoutDao(), workoutEntryDao()) }
    }

    @Singleton
    @Provides
    fun provideRoutineRepository(dumbStrengthDatabase: DumbStrengthDatabase): RoutineRepository {
        return with(dumbStrengthDatabase) { RoutineRepository(routineDao(), routineEntryDao()) }
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DumbStrengthDatabase{
        return Room.databaseBuilder(context.applicationContext, DumbStrengthDatabase::class.java,
            "Main.db")
            .createFromAsset("Main.db")
            .build()
    }
}