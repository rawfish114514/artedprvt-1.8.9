package rawfish.artedprvt.mi;

import net.minecraft.nbt.*;
import rawfish.artedprvt.api.Solvable;

/**
 * 标签生成器
 */
@Solvable
public class TagBuilder {
    @Solvable
    public static CompoundTagBuilder compound(){
        return new CompoundTagBuilder();
    }

    @Solvable
    public static ListTagBuilder list(){
        return new ListTagBuilder();
    }

    /**
     * 列表标签生成器
     */
    @Solvable
    public static class ListTagBuilder{
        private NBTTagList list;

        @Solvable
        public ListTagBuilder(){
            list=new NBTTagList();
        }

        @Solvable
        public ListTagBuilder setTag(int key,NBTBase tag){
            list.set(key,tag);
            return this;
        }

        @Solvable
        public ListTagBuilder addTag(NBTBase tag){
            list.appendTag(tag);
            return this;
        }

        @Solvable
        public ListTagBuilder setByte(int key,byte value){
            list.set(key,new NBTTagByte(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setShort(int key,short value){
            list.set(key,new NBTTagShort(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setInt(int key,int value){
            list.set(key,new NBTTagInt(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setLong(int key,long value){
            list.set(key,new NBTTagLong(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setFloat(int key,float value){
            list.set(key,new NBTTagFloat(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setDouble(int key,double value){
            list.set(key,new NBTTagDouble(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setByteArray(int key,byte[] value){
            list.set(key,new NBTTagByteArray(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setIntArray(int key,int[] value){
            list.set(key,new NBTTagIntArray(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setString(int key,String value){
            list.set(key,new NBTTagString(value));
            return this;
        }

        @Solvable
        public ListTagBuilder setList(int key,NBTTagList listTag){
            list.set(key,listTag);
            return this;
        }

        @Solvable
        public ListTagBuilder setList(int key,ListTagBuilder listBuilder){
            list.set(key,listBuilder.build());
            return this;
        }

        @Solvable
        public ListTagBuilder setCompound(int key,NBTTagCompound compoundTag){
            list.set(key,compoundTag);
            return this;
        }

        @Solvable
        public ListTagBuilder setCompound(int key,CompoundTagBuilder compoundBuilder){
            list.set(key,compoundBuilder.build());
            return this;
        }

        @Solvable
        public ListTagBuilder addByte(byte value){
            list.appendTag(new NBTTagByte(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addShort(short value){
            list.appendTag(new NBTTagShort(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addInt(int value){
            list.appendTag(new NBTTagInt(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addLong(long value){
            list.appendTag(new NBTTagLong(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addFloat(float value){
            list.appendTag(new NBTTagFloat(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addDouble(double value){
            list.appendTag(new NBTTagDouble(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addByteArray(byte[] value){
            list.appendTag(new NBTTagByteArray(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addIntArray(int[] value){
            list.appendTag(new NBTTagIntArray(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addString(String value){
            list.appendTag(new NBTTagString(value));
            return this;
        }

        @Solvable
        public ListTagBuilder addList(NBTTagList listTag){
            list.appendTag(listTag);
            return this;
        }

        @Solvable
        public ListTagBuilder addList(ListTagBuilder listBuilder){
            list.appendTag(listBuilder.build());
            return this;
        }

        @Solvable
        public ListTagBuilder addCompound(NBTTagCompound compoundTag){
            list.appendTag(compoundTag);
            return this;
        }

        @Solvable
        public ListTagBuilder addCompound(CompoundTagBuilder compoundBuilder){
            list.appendTag(compoundBuilder.build());
            return this;
        }

        @Solvable
        public ListTagBuilder remove(int key){
            list.removeTag(key);
            return this;
        }

        @Solvable
        public NBTTagList build(){
            return (NBTTagList)list.copy();
        }

        @Solvable
        public NBTTagList get(){
            return list;
        }

        @Override
        public String toString(){
            return list.toString();
        }
    }

    /**
     * 复合标签生成器
     */
    @Solvable
    public static class CompoundTagBuilder{
        private NBTTagCompound compound;

        @Solvable
        public CompoundTagBuilder(){
            compound=new NBTTagCompound();
        }

        @Solvable
        public CompoundTagBuilder setTag(String key,NBTBase tag){
            compound.setTag(key,tag);
            return this;
        }

        @Solvable
        public CompoundTagBuilder setByte(String key,byte value){
            compound.setTag(key,new NBTTagByte(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setShort(String key,short value){
            compound.setTag(key,new NBTTagShort(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setInt(String key,int value){
            compound.setTag(key,new NBTTagInt(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setLong(String key,long value){
            compound.setTag(key,new NBTTagLong(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setFloat(String key,float value){
            compound.setTag(key,new NBTTagFloat(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setDouble(String key,double value){
            compound.setTag(key,new NBTTagDouble(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setByteArray(String key,byte[] value){
            compound.setTag(key,new NBTTagByteArray(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setIntArray(String key,int[] value){
            compound.setTag(key,new NBTTagIntArray(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setString(String key,String value){
            compound.setTag(key,new NBTTagString(value));
            return this;
        }

        @Solvable
        public CompoundTagBuilder setList(String key,NBTTagList listTag){
            compound.setTag(key,listTag);
            return this;
        }

        @Solvable
        public CompoundTagBuilder setList(String key,ListTagBuilder listBuilder){
            compound.setTag(key,listBuilder.build());
            return this;
        }

        @Solvable
        public CompoundTagBuilder setCompound(String key,NBTTagCompound compoundTag){
            compound.setTag(key,compoundTag);
            return this;
        }

        @Solvable
        public CompoundTagBuilder setCompound(String key,CompoundTagBuilder compoundBuilder){
            compound.setTag(key,compoundBuilder.build());
            return this;
        }

        @Solvable
        public CompoundTagBuilder remove(String key){
            compound.removeTag(key);
            return this;
        }

        @Solvable
        public NBTTagCompound build(){
            return (NBTTagCompound)compound.copy();
        }

        @Solvable
        public NBTTagCompound get(){
            return compound;
        }

        @Override
        public String toString(){
            return compound.toString();
        }
    }
}
